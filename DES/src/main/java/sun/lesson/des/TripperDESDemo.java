package sun.lesson.des;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * @author: sun
 * @date: 2019/6/14
 */
public class TripperDESDemo {

    private static final String SOURCE = "Hello 3DES";

    public static void main(String[] args) {
        jdk3DES();
    }

    public static void jdk3DES() {
        try {
            // 1. 生成Key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
//            keyGenerator.init(168); // 初始化为168位
            keyGenerator.init(new SecureRandom()); // 根据算法生成默认长度的key
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encodedKey = secretKey.getEncoded();

            // 2. 转换Key
            DESedeKeySpec desKeySpec = new DESedeKeySpec(encodedKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey convertedKey = keyFactory.generateSecret(desKeySpec);

            // 3. 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding"); // 加密算法/工作模式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE, convertedKey);
            byte[] result = cipher.doFinal(SOURCE.getBytes());
            System.out.println("JDK 3DES Encrypt 0x:" + Hex.encodeHexString(result));
            System.out.println("JDK 3DES Encrypt Base64:" + new String(Base64.encode(result)));

            // 4. 解密 对称加密的密钥是相同的 所以这里的密钥产生过程是意义的
            cipher.init(Cipher.DECRYPT_MODE, convertedKey);
            result = cipher.doFinal(result);
            System.out.println("JDK 3DES Decrypt:" + new String(result));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
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
