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

    /**
     * JAVA6支持以下任意一种算法
     * PBEWITHMD5ANDDES 盐的长度为8个字节
     * PBEWITHMD5ANDTRIPLEDES
     * PBEWITHSHAANDDESEDE
     * PBEWITHSHA1ANDRC2_40
     * PBKDF2WITHHMACSHA1
     */
    public static final String ALGORITHM = "PBEWITHMD5ANDDES";

    private static final String SOURCE = "Hello PBE";

    public static void main(String[] args) {
        jdkPBE();
    }

    private static void jdkPBE() {
        try {
            // 1. 初始化盐
            SecureRandom random = new SecureRandom();
            byte[] salt = random.generateSeed(8);

            // 2. 生成密钥（口令转密钥）
            String password = "sun-lesson";
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey key = factory.generateSecret(pbeKeySpec);

            // 3. 加密
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100); // 盐和需要迭代的次数
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
            byte[] result = cipher.doFinal(SOURCE.getBytes());
            System.out.println("JDK " + ALGORITHM + " Encrypt 0x:" + Hex.encodeHexString(result));
            System.out.println("JDK " + ALGORITHM + " Encrypt Base64:" + new String(Base64.encode(result)));

            // 4. 解密
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
            result = cipher.doFinal(result);
            System.out.println("JDK" + ALGORITHM + "Decrypt Base64:" + new String(result));

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
