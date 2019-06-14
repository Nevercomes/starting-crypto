package sun.lesson.md;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * @author: sun
 * @date: 2019/6/14
 */
public class MDDemo {

    private static final String SOURCE = "Hello MD";

    public static void main(String[] args) {
        jdkMD5();
        jdkMD2();
        bcMD4();
        bcMD5();
        ccMD5();
    }

    public static void jdkMD5() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(SOURCE.getBytes());
            System.out.println("JDK MD5: " + Hex.encodeHexString(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void jdkMD2() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] digest = md.digest(SOURCE.getBytes());
            System.out.println("JDK MD2: " + Hex.encodeHexString(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void bcMD5() {
        Digest digest = new MD4Digest();
        digest.update(SOURCE.getBytes(), 0, SOURCE.getBytes().length);
        byte[] md5Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md5Bytes, 0);
        System.out.println("BC MD5: " + org.bouncycastle.util.encoders.Hex.toHexString(md5Bytes));
    }

    public static void bcMD4() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            MessageDigest md = MessageDigest.getInstance("MD4");
            byte[] digest = md.digest(SOURCE.getBytes());
            System.out.println("JDK MD4: " + Hex.encodeHexString(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Digest digest = new MD4Digest();
        digest.update(SOURCE.getBytes(), 0, SOURCE.getBytes().length);
        byte[] md4Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md4Bytes, 0);
        System.out.println("BC MD4: " + org.bouncycastle.util.encoders.Hex.toHexString(md4Bytes));
    }

    public static void ccMD5() {
        System.out.println("CC MD5: " + DigestUtils.md5Hex(SOURCE));
    }

}
