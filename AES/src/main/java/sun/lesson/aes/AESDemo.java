package sun.lesson.aes;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * @author: sun
 * @date: 2019/6/14
 */
public class AESDemo {

    private static final String SOURCE = "Hello AES";

    public static void main(String[] args) {
        jdkDES();
    }

    public static void jdkDES() {
        try {
            // 1. 生成Key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encodedKey = secretKey.getEncoded();

            // 2. 转换Key
            SecretKeySpec aesKeySpec = new SecretKeySpec(encodedKey, "AES");

            // 3. 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // 加密算法/工作模式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE, aesKeySpec);
            byte[] result = cipher.doFinal(SOURCE.getBytes());
            System.out.println("JDK AES Encrypt 0x:" + Hex.encodeHexString(result));
            System.out.println("JDK AES Encrypt Base64:" + new String(Base64.encode(result)));

            // 4. 解密 对称加密的密钥是相同的 所以这里的密钥产生过程是意义的
            cipher.init(Cipher.DECRYPT_MODE, aesKeySpec);
            result = cipher.doFinal(result);
            System.out.println("JDK AES Decrypt:" + new String(result));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

}
