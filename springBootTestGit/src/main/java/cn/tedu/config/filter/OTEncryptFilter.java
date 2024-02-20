package cn.tedu.config.filter;


import cn.tedu.Utils.AESUtils;
import cn.tedu.Utils.HttpRequestUtils;
import cn.tedu.Utils.RSAUtils;
import cn.tedu.Utils.RosBeanUtil;
import cn.tedu.config.filter.request.OtWrapperedRequestBackkup;
import cn.tedu.config.filter.response.WrapperedResponse;
import cn.tedu.enums.ProcessCodeEnum;
import cn.tedu.inputDTO.EncryptBaseInputDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


/**
 * 加解密,验签
 *
 */
@Slf4j
@Component
public class OTEncryptFilter implements Filter {

    public static final String  privtaeKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJoXuGT5VlK+qj2iabIpz8/svZ89UKLrMWLXNb/NpSJSdkz06ILPWRJ28WexcIV4tOjtye3KK+Um3guBTHUO2WRyILC+EuccIdPyaHN1Nj+PRhCEEBoyHAXCwkYYFKuzBzLL12e9OjfxXh83/FK53RDA87vmpXtPFk58AuaTk+wNAgMBAAECgYAb5bl7hmyrdFtr5zCNkeZOtYSrBLXJmR0K0P2pKPkBmr5SNnC15gYxQpl39XbyoyTVmt9ZDqYdyYifupv2AWc38E97tJJI9vdmYROtS3PU9qaVvPWRvFR+Uy71QAuh9h8ge3unZ/ZV0tmAqNcpc+eOAp6H5yfT7drLQ99t9lrpvQJBAOpEvqNd/xnr+xy2sD6qyemY7qqOGOTCcEHRPKx3k9Jinp3IQmRxYIPU4msK70jVDFsyVne6aMmiakQR0Ot4w/MCQQCoYwL0PJuwTLtUeWBTwYqx6299erKHtU+wxQMkQ53/55pF1UJ7l6GR7Yich7PapV+Ndx7BwSt4VyZKp58k3I//AkEAvSvf6VnI2mzFLM2VCjWtsDUvXIg8L9WXAn7siLRSqMu8G9sPSb6H2ky4vQlqahynyqieTDY50cCWIPtmAg9V4wJAAWCN7aFxRIfcIFy9xPGYSMw8JTKTszhwZmm2FN3YtPcX8+pCmPAsihz/OQiDGA6yMV4ACmWiDWHpFkM4sfx70QJAMuGVKNpTJNWD2Ounk8ldBB4W2i875NltCVzNKcNi22uiIaLMTKhJTtQR+UjEMQYGhLlgBDQL8qQyjcXcTBQ17w==";
    public static final String  secret = "bd3d80982cae4ed0";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        log.info("===== EncryptFilter doFiter.uri ==== < {} >   ", uri);

        JSONObject reqJsonObj = HttpRequestUtils.getPostRequestJsonObj(httpServletRequest);// 非第一个处理基类参数的过滤器，从输入流中获取入参
        EncryptBaseInputDTO inputDTO = RosBeanUtil.copyEntry(reqJsonObj, EncryptBaseInputDTO.class);
        //校验入参 必输参数校验
        log.info("校验入参 必输参数校验inputDTO={}", inputDTO);

        inputDTO.validateParams();
        // 获取秘钥配置信息
        this.verifySign(inputDTO, secret);
        //解密业务参数
        String aeskey = RSAUtils.decryptByPrivateKey(privtaeKey, inputDTO.getEncodeKey());
        String requestDataDec = AESUtils.decrypt(inputDTO.getRequestDataEnc(), aeskey);
        log.info("解密的业务参数requestDataDec= {}", requestDataDec);

        //更新请求入参
        OtWrapperedRequestBackkup  OtWrapperedRequestBackkup  = this.freshParams(httpServletRequest,requestDataDec);
        WrapperedResponse  wrapperedResponse  = new WrapperedResponse((HttpServletResponse) response);
        chain.doFilter(OtWrapperedRequestBackkup,wrapperedResponse);

        //加密返回数据
        this.encryptResponse(response,wrapperedResponse,aeskey);
        log.info("加密返回数据中的业务参数结束");

    }


    /**
     * 加密返回数据中的业务参数
     *
     * @param response
     * @param wrapperedResponse
     * @param aesKey
     */
    private  void encryptResponse(ServletResponse response,WrapperedResponse wrapperedResponse,String aesKey) throws  IOException{
      log.info("加密返回数据中的业务参数");
      String reponseStr = wrapperedResponse.getResponseDataStr();
      if(StringUtils.isBlank(reponseStr)){
          return;
      }

      JSONObject resObj = JSON.parseObject(reponseStr);
      if(null == resObj){
          return;
      }

      String resultCode = resObj.getString("resultCode");
      String resultObject  = resObj.getString("resultObject");
      if(ProcessCodeEnum.SUCCESS.equalsCode(resultCode) && null != resultCode){
          resultObject = AESUtils.encrypt(resultObject,aesKey);
          resObj.put("resultObject",resultObject);
      }

      String reponseStrEnc = JSON.toJSONString(resObj);


    HttpRequestUtils.writeResponse(response,reponseStrEnc);


    }


    private OtWrapperedRequestBackkup freshParams(HttpServletRequest request, String requestDataDec) {
        log.info("EncryptFilter 更新请求入参");
        //获取数据
        JSONObject jsonObject = HttpRequestUtils.getPostRequestJsonObj(request);//非第一个处理基类参数的过滤器，从输入流中获取入参
        JSONObject jsonObjectDec = JSON.parseObject(requestDataDec);
        if (null != jsonObjectDec) {
            jsonObject.putAll(jsonObjectDec);
        }
        //更新请求参数
        OtWrapperedRequestBackkup wrapperedRequestBackkup = HttpRequestUtils.freshParams(request, jsonObject);

       return  wrapperedRequestBackkup;
    }



    /**
     * 验签
     * @param encryptBaseInputDTO
     * @param secret
     */
    private void verifySign(EncryptBaseInputDTO encryptBaseInputDTO,String secret)  {
        Map<String, String> signDataMap = JSON.parseObject(JSON.toJSONString(encryptBaseInputDTO), TreeMap.class);
        signDataMap.remove("sign");
        signDataMap.put("secret", secret);
        String str = signDataMap.toString();
        log.info("加密前str={}",str);
        String sha256Sign = DigestUtils.sha256Hex(str.trim());
        if (!encryptBaseInputDTO.getSign().equals(sha256Sign)) {
            log.info("signDataMap.toString= {} ", str);
            log.warn("验签失败 sha256Sign ={}，encryptBaseInputDTO.getSign = {} ", sha256Sign, encryptBaseInputDTO.getSign());
            throw ProcessCodeEnum.PARM_WARM.buildException("验签失败");
        }
    }

    public static void main(String[] args) {
        String str1 = "{clientId=aiaos, encodekey=NWs2sJeH2tvRq+4IOYcJ+LtZE7jwJ0H+5TgXKX1twXZFS3Pm0cmS7sgxZ0FPbR70I1oAf/+P3gfvb9IUsAPhWP86D0AQ2GlmaoBqGGKcJxrz5JUjy1KZcFNIaaWty4qXR2DCUsFtekCZylmxFcUaE3Ikm1UPu9MsdphLyDXB58g=, requestDataEnc=VPpnEYCEBshYiAZ4F9LmoQ==, requestId=3ae9fa158f484028, secret=bd3d80982cae4ed0, timestamp=1652007925086}\n";
        String sha256Sign0 = DigestUtils.sha256Hex(str1.trim());

        log.info("sha256Sign1.toString= {} ", sha256Sign0);


        String str2 = "{clientId=aiaos, encodeKey=NWs2sJeH2tvRq+4IOYcJ+LtZE7jwJ0H+5TgXKX1twXZFS3Pm0cmS7sgxZ0FPbR70I1oAf/+P3gfvb9IUsAPhWP86D0AQ2GlmaoBqGGKcJxrz5JUjy1KZcFNIaaWty4qXR2DCUsFtekCZylmxFcUaE3Ikm1UPu9MsdphLyDXB58g=, requestDataEnc=VPpnEYCEBshYiAZ4F9LmoQ==, requestId=3ae9fa158f484028, secret=bd3d80982cae4ed0, timestamp=1652007925086}\n";
        String sha256Sign2 = DigestUtils.sha256Hex(str2.trim());
        log.info("sha256Sign2.toString= {} ", sha256Sign2);


    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

        @Override
    public void destroy() {

    }
}
