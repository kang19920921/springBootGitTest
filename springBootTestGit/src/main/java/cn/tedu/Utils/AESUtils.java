package cn.tedu.Utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.transform.Source;
import java.nio.charset.StandardCharsets;

@Slf4j
@Getter
@Setter
public class AESUtils {
    private static final String KEY_ALGORITHM = "AES";
    /*** 默认的加密算法 */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    //向量
    private static final String IV_SEED = "1234567812345678";

    public static String encrypt(String str, String key) {

        try {
            if (null == str) {
                log.info("AES加密出错:key为空null");
                return null;
            }
            //判断key是否16位
            if (key.length() != 16) {
                log.info("AES加密出错:key不是16位");
                return null;
            }

            byte[]  raw = key.getBytes(StandardCharsets.UTF_8);
            //创建获取秘钥
            SecretKeySpec  secretKeySpec  =  new SecretKeySpec(raw,KEY_ALGORITHM);
            //创建密码器
            Cipher  cipher  = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            IvParameterSpec  iv  = new IvParameterSpec(IV_SEED.getBytes(StandardCharsets.UTF_8));
            //密码器初始化
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,iv);
            byte[] srawt  = str.getBytes(StandardCharsets.UTF_8);
            int len = srawt.length;
            //
            /** 计算补空格后的长度 **/
            while (len  %  16 !=0){
                len++;
            }
            byte [] sraw  =  new byte[len];
            /** 在最后空格  **/
            for (int i = 0; i < len; ++i) {
                if( i < srawt.length){
                    sraw[i] = srawt[i];
                } else {
                    sraw[i] = 32 ;
                }
            }
            byte[] encypted = cipher.doFinal(sraw);
           return  formatString(new String(Base64.encodeBase64(encypted),StandardCharsets.UTF_8));

        } catch (Exception e) {
            log.error("AES加密出错： " + e.toString());
            return null;

        }
    }

    private static String formatString(String source) {
        if (null == source) {
            return null;
        }

        return source.replaceAll("\\r", "").replaceAll("\\n", "");
    }


}