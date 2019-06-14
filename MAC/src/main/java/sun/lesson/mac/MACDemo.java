package sun.lesson.mac;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author: sun
 * @date: 2019/6/14
 */
public class MACDemo {

    private static final String SOURCE = "Hello HMAC";

    public static void main(String[] args) {
        jdkHmacMD5();
        bcHmacMD5();
    }

    public static void jdkHmacMD5() {
        try {
            // 1. 初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
            // 2. 产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 3. 获得密钥
            byte[] key = secretKey.getEncoded();
            // 3.1 自定义密钥
            key = Hex.decodeHex(new char[]{'7', 'A', '6', 'F'});
            // 4. 还原密钥
            SecretKey restoreSecretKey = new SecretKeySpec(key, "HmacMD5");
            // 5. 实例化MAC
            Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
            // 6. 初始化MAC
            mac.init(restoreSecretKey);
            // 7. 执行摘要
            byte[] hmacMD5Bytes = mac.doFinal(SOURCE.getBytes());
            System.out.println("JDK HmacMD5: " + Hex.encodeHexString(hmacMD5Bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

    public static void bcHmacMD5() {
        HMac hMac = new HMac(new MD5Digest());
        hMac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("7A6F")));

        hMac.update(SOURCE.getBytes(), 0, SOURCE.getBytes().length);
        byte[] hmacMD5Bytes = new byte[hMac.getMacSize()];
        hMac.doFinal(hmacMD5Bytes, 0);
        System.out.println("BC HmacMD5: " + org.bouncycastle.util.encoders.Hex.toHexString(hmacMD5Bytes));
    }

}
