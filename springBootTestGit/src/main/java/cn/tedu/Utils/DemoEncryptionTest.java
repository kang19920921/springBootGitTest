package cn.tedu.Utils;

import cn.tedu.inputDTO.FindAllListInputDTO;
import cn.tedu.enums.ProcessCodeEnum;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.TreeMap;


@Slf4j
public class DemoEncryptionTest {
    public static final String  privtaeKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJoXuGT5VlK+qj2iabIpz8/svZ89UKLrMWLXNb/NpSJSdkz06ILPWRJ28WexcIV4tOjtye3KK+Um3guBTHUO2WRyILC+EuccIdPyaHN1Nj+PRhCEEBoyHAXCwkYYFKuzBzLL12e9OjfxXh83/FK53RDA87vmpXtPFk58AuaTk+wNAgMBAAECgYAb5bl7hmyrdFtr5zCNkeZOtYSrBLXJmR0K0P2pKPkBmr5SNnC15gYxQpl39XbyoyTVmt9ZDqYdyYifupv2AWc38E97tJJI9vdmYROtS3PU9qaVvPWRvFR+Uy71QAuh9h8ge3unZ/ZV0tmAqNcpc+eOAp6H5yfT7drLQ99t9lrpvQJBAOpEvqNd/xnr+xy2sD6qyemY7qqOGOTCcEHRPKx3k9Jinp3IQmRxYIPU4msK70jVDFsyVne6aMmiakQR0Ot4w/MCQQCoYwL0PJuwTLtUeWBTwYqx6299erKHtU+wxQMkQ53/55pF1UJ7l6GR7Yich7PapV+Ndx7BwSt4VyZKp58k3I//AkEAvSvf6VnI2mzFLM2VCjWtsDUvXIg8L9WXAn7siLRSqMu8G9sPSb6H2ky4vQlqahynyqieTDY50cCWIPtmAg9V4wJAAWCN7aFxRIfcIFy9xPGYSMw8JTKTszhwZmm2FN3YtPcX8+pCmPAsihz/OQiDGA6yMV4ACmWiDWHpFkM4sfx70QJAMuGVKNpTJNWD2Ounk8ldBB4W2i875NltCVzNKcNi22uiIaLMTKhJTtQR+UjEMQYGhLlgBDQL8qQyjcXcTBQ17w==";
    public static final String  publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCaF7hk+VZSvqo9ommyKc/P7L2fPVCi6zFi1zW/zaUiUnZM9OiCz1kSdvFnsXCFeLTo7cntyivlJt4LgUx1DtlkciCwvhLnHCHT8mhzdTY/j0YQhBAaMhwFwsJGGBSrswcyy9dnvTo38V4fN/xSud0QwPO75qV7TxZOfALmk5PsDQIDAQAB";
    public static final String  secret = "123456789";

    public static void main(String[] args) {
        log.info("第一次加密调用测试,努努努力");
        String url = "http://localhost:8070/user/findAll";
        try {
            String aesKey = UUIDUtils.getUUID16();
            log.info("AES_KEY={}", aesKey);



            FindAllListInputDTO findAllListInputDTO = new FindAllListInputDTO();
            findAllListInputDTO.setIdUser(6);
            String strParam = buildRequestParams(findAllListInputDTO, UUIDUtils.getUUID16(), aesKey);
            //请求
            String resultJSONOStr= HttpClientUtils.httpPost(url, strParam);
            //解析返回结果
            parseResponse(resultJSONOStr,aesKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 世上无难事，只要肯攀登
     * @param businessParams
     * @param requestId
     * @param aesKey
     * @return
     */
    private static String  buildRequestParams(Object businessParams, String requestId, String  aesKey) {
        Map<String, String> reqMap = new TreeMap<String, String>();
        reqMap.put("clientId", "aiaos");
        reqMap.put("requestId", requestId);
        reqMap.put("timestamp", String.valueOf(System.currentTimeMillis()));

        //AES加密业务参数
        System.out.println("明文业务数据businessParams=");
        System.out.println(businessParams);
        System.out.println("\r\n");
        String requestDataEnc = AESUtils.encrypt(JSONObject.toJSONString(businessParams), aesKey);
        reqMap.put("requestDataEnc", requestDataEnc);
        System.out.println("用AES算法加密后的业务数据");
        System.out.println(requestDataEnc);
        System.out.println("\r\n");

        //RSA加密 aeskey
        String encodeKey = RSAUtils.encryptByPublicKey(publicKey, aesKey);
        reqMap.put("encodekey", encodeKey);

        //签名
        reqMap.put("secret", secret);
        String str = reqMap.toString();
        String sha256Sign = DigestUtils.sha256Hex(str);
        reqMap.put("sign", sha256Sign);
        System.out.println("签名sha256Sign");
        System.out.println("\r\n");
        System.out.println("\r\n");
        reqMap.remove("secret");

        String reqMapJSONStr = JSONObject.toJSONString(reqMap);
        System.out.println("请求参数： ");
        System.out.println(reqMapJSONStr);
        System.out.println("\r\n");

        return reqMapJSONStr;
    }


   private static  String parseResponse(String resultStr,String aesKey) {
       log.info("请求接口返回的结果resultStr= " + resultStr);
       if (StringUtils.isBlank(resultStr)) {
           log.info("result is null");
           throw new RuntimeException("接口请求异常，请稍后重试");
       }

       JSONObject responseJson = null;
       try {
           responseJson = JSON.parseObject(resultStr);
       } catch (Exception ex) {
           throw new RuntimeException("接口请求异常，请稍后重试,e");
       }

       if (null == responseJson) {
           log.info("responseJson is blank");
           throw new RuntimeException("接口请求异常，请稍后重试");
       }

       String responseCode = responseJson.getString("resultCode");
       if (!"0000".equals(responseCode)) {//业务异常
           String responseMsg = responseJson.getString("resultMsg");
           ProcessCodeEnum.PROCESS_ERR.throwException(responseMsg);
       }

       String responseDataEnc = responseJson.getString("resultObject");//密文
       if (StringUtils.isBlank(responseDataEnc)) {
           return null;
       }

       String responseDataDec = AESUtils.decrypt(responseDataEnc, aesKey);//明文
       log.info("解密后的明文responseDataDec={} ", resultStr);

       return responseDataDec;
   }













}
