package sun.lesson.des;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

/**
 * @author: sun
 * @date: 2019/6/14
 */
public class DESDemo {

    private static final String SOURCE = "Hello DES";

    public static void main(String[] args) {
        jdkDES();
        bcDES();
    }

    public static void jdkDES() {
        try {
            // 1. 生成Key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56); // 初始化为56位
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encodedKey = secretKey.getEncoded();

            // 2. 转换Key
            DESKeySpec desKeySpec = new DESKeySpec(encodedKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey convertedKey = keyFactory.generateSecret(desKeySpec);

            // 3. 加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); // 加密算法/工作模式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE, convertedKey);
            byte[] result = cipher.doFinal(SOURCE.getBytes());
            System.out.println("JDK DES-56 Encrypt 0x:" + Hex.encodeHexString(result));
            System.out.println("JDK DES-56 Encrypt Base64:" + new String(Base64.encode(result)));

            // 4. 解密 对称加密的密钥是相同的 所以这里的密钥产生过程是意义的
            cipher.init(Cipher.DECRYPT_MODE, convertedKey);
            result = cipher.doFinal(result);
            System.out.println("JDK DES-56 Decrypt:" + new String(result));

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

    public static void bcDES() {
        Security.addProvider(new BouncyCastleProvider());

        try {
            // 1. 生成Key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES", "BC"); // 这里需要指明Provider
            keyGenerator.init(56); // 初始化为56位
//            keyGenerator.getProvider();
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encodedKey = secretKey.getEncoded();

            // 2. 转换Key
            DESKeySpec desKeySpec = new DESKeySpec(encodedKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey convertedKey = keyFactory.generateSecret(desKeySpec);

            // 3. 加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); // 加密算法/工作模式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE, convertedKey);
            byte[] result = cipher.doFinal(SOURCE.getBytes());
            System.out.println("BC DES-56 Encrypt 0x:" + Hex.encodeHexString(result));
            System.out.println("BC DES-56 Encrypt Base64:" + new String(Base64.encode(result)));

            // 4. 解密 对称加密的密钥是相同的 所以这里的密钥产生过程是意义的
            cipher.init(Cipher.DECRYPT_MODE, convertedKey);
            result = cipher.doFinal(result);
            System.out.println("BC DES-56 Decrypt:" + new String(result));

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
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

}
