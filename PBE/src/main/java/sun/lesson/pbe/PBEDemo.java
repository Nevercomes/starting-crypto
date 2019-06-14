package sun.lesson.pbe;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * @author: sun
 * @date: 2019/6/14
 */
public class PBEDemo {

    private static final String SOURCE = "Hello PBE";

    public static void main(String[] args) {
        jdkPBE();
    }

    private static void jdkPBE() {
        try {
            // 1. 初始化盐
            SecureRandom random = new SecureRandom();
            byte[] salt = SecureRandom.getSeed(8);

            // 2. 生成密钥（口令转密钥）
            String password = "sun-lesson";
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            SecretKey key = factory.generateSecret(pbeKeySpec);

            // 3. 加密
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100); // 盐和需要迭代的次数
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
            byte[] result = cipher.doFinal(SOURCE.getBytes());
            System.out.println("JDK PBEWITHMD5andDES Encrypt 0x:" + Hex.encodeHexString(result));
            System.out.println("JDK PBEWITHMD5andDES Encrypt Base64:" + new String(Base64.encode(result)));

            // 4. 解密
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
            result = cipher.doFinal(result);
            System.out.println("JDK PBEWITHMD5andDES Decrypt Base64:" + new String(result));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

    }

}
