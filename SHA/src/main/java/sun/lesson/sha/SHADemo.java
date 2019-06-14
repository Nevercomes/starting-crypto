package sun.lesson.sha;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * @author: sun
 * @date: 2019/6/14
 */
public class SHADemo {

    private final static String SOURCE = "Hello SHA";

    public static void main(String[] args) {
        jdkSHA1();
        bcSHA1();
        bcSHA224();
        ccSHA512();
    }

    public static void jdkSHA1() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] digestBytes = md.digest(SOURCE.getBytes());
            System.out.println("JDK SHA-1: " + Hex.encodeHexString(digestBytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void bcSHA1() {
        Digest digest = new SHA1Digest();
        digest.update(SOURCE.getBytes(), 0, SOURCE.getBytes().length);
        byte[] sha1Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha1Bytes, 0);
        System.out.println("BC SHA-1: " + org.bouncycastle.util.encoders.Hex.toHexString(sha1Bytes));
    }

    public static void bcSHA224() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] digestBytes = md.digest(SOURCE.getBytes());
            System.out.println("JDK SHA-224: " + Hex.encodeHexString(digestBytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Digest digest = new SHA224Digest();
        digest.update(SOURCE.getBytes(), 0, SOURCE.getBytes().length);
        byte[] sha224Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha224Bytes, 0);
        System.out.println("BC SHA-224: " + org.bouncycastle.util.encoders.Hex.toHexString(sha224Bytes));
    }

    public static void ccSHA512() {
        System.out.println("CC SHA-521: " + DigestUtils.sha512Hex(SOURCE.getBytes()));
    }


}
