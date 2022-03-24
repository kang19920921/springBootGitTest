package cn.tedu.Utils;

import org.apache.commons.codec.digest.DigestUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.TreeMap;


@Slf4j
public class DemoEncryptionTest {
    public static final String  privtaeKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJoXuGT5VlK+qj2iabIpz8/svZ89UKLrMWLXNb/NpSJSdkz06ILPWRJ28WexcIV4tOjtye3KK+Um3guBTHUO2WRyILC+EuccIdPyaHN1Nj+PRhCEEBoyHAXCwkYYFKuzBzLL12e9OjfxXh83/FK53RDA87vmpXtPFk58AuaTk+wNAgMBAAECgYAb5bl7hmyrdFtr5zCNkeZOtYSrBLXJmR0K0P2pKPkBmr5SNnC15gYxQpl39XbyoyTVmt9ZDqYdyYifupv2AWc38E97tJJI9vdmYROtS3PU9qaVvPWRvFR+Uy71QAuh9h8ge3unZ/ZV0tmAqNcpc+eOAp6H5yfT7drLQ99t9lrpvQJBAOpEvqNd/xnr+xy2sD6qyemY7qqOGOTCcEHRPKx3k9Jinp3IQmRxYIPU4msK70jVDFsyVne6aMmiakQR0Ot4w/MCQQCoYwL0PJuwTLtUeWBTwYqx6299erKHtU+wxQMkQ53/55pF1UJ7l6GR7Yich7PapV+Ndx7BwSt4VyZKp58k3I//AkEAvSvf6VnI2mzFLM2VCjWtsDUvXIg8L9WXAn7siLRSqMu8G9sPSb6H2ky4vQlqahynyqieTDY50cCWIPtmAg9V4wJAAWCN7aFxRIfcIFy9xPGYSMw8JTKTszhwZmm2FN3YtPcX8+pCmPAsihz/OQiDGA6yMV4ACmWiDWHpFkM4sfx70QJAMuGVKNpTJNWD2Ounk8ldBB4W2i875NltCVzNKcNi22uiIaLMTKhJTtQR+UjEMQYGhLlgBDQL8qQyjcXcTBQ17w==";
    public static final String  publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCaF7hk+VZSvqo9ommyKc/P7L2fPVCi6zFi1zW/zaUiUnZM9OiCz1kSdvFnsXCFeLTo7cntyivlJt4LgUx1DtlkciCwvhLnHCHT8mhzdTY/j0YQhBAaMhwFwsJGGBSrswcyy9dnvTo38V4fN/xSud0QwPO75qV7TxZOfALmk5PsDQIDAQAB";
    public static final String  secret = "123456789";

    public static void main(String[] args) {
       log.info("第一次加密调用测试,努努努力");
       String url = "127.0.0.1:90";
        try {
//            buildRequestParams();
//         HttpClientUtils.httpPost()

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













}
