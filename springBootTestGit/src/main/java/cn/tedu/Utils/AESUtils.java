package cn.tedu.Utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Getter
@Setter
public class AESUtils {
    private static final String KEY_ALGORITHM = "AES";
    /*** 默认的加密算法 */
    private static final String CIPHER_ALGORITHM = "AES/CBC/NOPadding";
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

            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            //创建获取秘钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            //创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(StandardCharsets.UTF_8));
            //密码器初始化
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] srawt = str.getBytes(StandardCharsets.UTF_8);
            int len = srawt.length;
            //
            /** 计算补空格后的长度 **/
            while (len % 16 != 0) {
                len++;
            }
            byte[] sraw = new byte[len];
            /** 在最后空格  **/
            for (int i = 0; i < len; ++i) {
                if (i < srawt.length) {
                    sraw[i] = srawt[i];
                } else {
                    sraw[i] = 32;
                }
            }
            byte[] encypted = cipher.doFinal(sraw);
            return formatString(new String(Base64.encodeBase64(encypted), StandardCharsets.UTF_8));

        } catch (Exception e) {
            log.error("AES加密出错： " + e.toString());
            return null;

        }
    }


    /**
     * AES解密算法
     *
     * @param str
     * @param key
     * @return
     */
    public static String decrypt(String str, String key) {
        try {
            if (null == str) {
                log.info("AES解密出错:key为空null");
                return null;
            }
            //判断key是否16位
            if (key.length() != 16) {
                log.info("AES解密出错:key不是16位");
                return null;
            }
            //创建秘钥
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            //创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] bytes = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
            bytes = cipher.doFinal(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("AES解密出错： " + e.toString());
            return null;
        }

    }

    private static String formatString(String source) {
        if (null == source) {
            return null;
        }

        return source.replaceAll("\\r", "").replaceAll("\\n", "");
    }


    public static void main(String[] args) {
        String aeskey = UUIDUtils.getUUID16();
        String source = "第一次AES加解密------=======------";
        //加密
        String encrypt_str = encrypt(source, aeskey);
        log.info("encrypt_str={}", encrypt_str);

        //解密
        String dencrypt_str = decrypt(encrypt_str, aeskey);
        log.info("dencrypt_str={}", dencrypt_str);

    }


}