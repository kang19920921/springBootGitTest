package cn.tedu.Utils;

import cn.tedu.config.filter.request.OtWrapperedRequest;
import cn.tedu.config.filter.request.OtWrapperedRequestBackkup;
import cn.tedu.enums.ProcessCodeEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpRequestUtils {

    private static final String APPLICATION_JSON = ContentType.APPLICATION_JSON.getMimeType();

    private static final String MULTIPART_FORM_DATA = ContentType.MULTIPART_FORM_DATA.getMimeType();


    /**
     * 更新请求入参
     * @param request
     * @param newJSONObj
     * @return
     */
    public static OtWrapperedRequestBackkup freshParams(HttpServletRequest request,JSONObject newJSONObj){
        String newParamJsonStr = JSONObject.toJSONString(newJSONObj);
        log.info("更新后的请求入参newParamJSONStr = {}",newParamJsonStr);
        OtWrapperedRequestBackkup  otWrapperedRequestBackkup = new OtWrapperedRequestBackkup(request,newParamJsonStr);
        return  otWrapperedRequestBackkup;

    }

    /**
     * 根据请求方法和ContentType才用不同的方式获取入参
     *
     * @param request
     * @return
     */
    public static JSONObject getParamsJsonObj(HttpServletRequest request) {
        String paramJSONStr = getParamJsonStr(request);
        JSONObject resultObject = JSONObject.parseObject(paramJSONStr);
        if (null == resultObject) {
            resultObject = new JSONObject();
        }

        return resultObject;
    }


    /**
     *
     * @param request
     * @return
     */
   private static String getParamJsonStr(HttpServletRequest request){

       try{
           String method = request.getMethod();
           String contentType = request.getContentType();
           log.info("method = {} , ContentType = {}",method,contentType);

           String  resultStr = null ;
           if("get".equals(method)){
               resultStr  = getGetRequestStr(request);
           }else{
             if(null == contentType){
                 contentType = "";
             }

             if(contentType.startsWith(APPLICATION_JSON)){
                 resultStr  = getPostRequestStr(request);
             }else if (contentType.startsWith(MULTIPART_FORM_DATA)){
                 resultStr  = getGetRequestStr(request);
             }else{//getFORMRequestStr
                 resultStr  = getGetRequestStr(request);
             }


           }

           log.info("获取到的请求入参resultStr= {} ",resultStr);
           return  resultStr;
       }catch (Exception exception){
           throw ProcessCodeEnum.PARM_WARM.buildException("获取请求参数异常",exception);
       }
   }

   private  static String getGetRequestStr(HttpServletRequest request){
       Map<String,Object> params = new HashMap<>();
       Enumeration<String> enums = request.getParameterNames();
       while(enums.hasMoreElements()){
           String key = enums.nextElement();
           String value = request.getParameter(key);
           if("NULL".equalsIgnoreCase(value)){
               log.info("获取到的值是null字符串，将该值设为null, key={}, value = {}",key,value);
               value = null;
           }
           params.put(key,value);
       }
        return JSONObject.toJSONString(params);

   }




   public static String getPostRequestStr(HttpServletRequest request) {
       try {
           log.info("按照post请求的方式从输入流中获取入参");
           byte[] buf = getPostRequestBytes(request);
           String charEncoding = request.getCharacterEncoding();
           if (charEncoding == null) {
               charEncoding = "UTF-8";
           }
           return new String(buf, charEncoding);

       } catch (IOException e) {
           e.printStackTrace();
           throw ProcessCodeEnum.PARM_WARM.buildException("获取请求参数异常",e);
       }
   }

    /**
     * 获取post请求的入参
     * @param request
     * @return
     */
   public static JSONObject getPostRequestJsonObj(HttpServletRequest request){
       String str = getPostRequestStr(request);
       JSONObject  resultJsonObj = JSONObject.parseObject(str);
       if(null == resultJsonObj){
           resultJsonObj = new JSONObject();
       }

     return  resultJsonObj;
   }


    /**
     * 获取输入流，转为byte数组
     * @param request
     * @return
     * @throws IOException
     */
   private static byte[] getPostRequestBytes(HttpServletRequest request) throws IOException {
       try(ServletInputStream in = request.getInputStream()) {
          return StreamUtils.copyToByteArray(in);
       }
   }

    public static void writeResponse(ServletResponse response, String reponseStrEnc) throws IOException {
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding("UTF-8");
        OutputStream out = response.getOutputStream();
        out.write(reponseStrEnc.getBytes("UTF-8"));
        out.flush();
    }
}
