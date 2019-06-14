package sun.lesson.base64;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;


/**
 * @author: sun
 * @date: 2019/6/14
 */
public class Base64Demo {

    public static final String CLEAR_TEXT = "Hello Base64Demo";

    public static void main(String[] args) {
        jdkBase64();
        ccBase64();
        bcBase64();
    }

    public static void jdkBase64() {
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(CLEAR_TEXT.getBytes());
        System.out.println(encode);

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            System.out.println(new String(decoder.decodeBuffer(encode)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ccBase64() {
        byte[] encodeBytes = Base64.encodeBase64(CLEAR_TEXT.getBytes());
        System.out.println(new String(encodeBytes));

        byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
        System.out.println(new String(decodeBytes));

    }

    public static void bcBase64() {
        byte[] encodeBytes = org.bouncycastle.util.encoders.Base64.encode(CLEAR_TEXT.getBytes());
        System.out.println(new String(encodeBytes));

        byte[] decodeBytes = org.bouncycastle.util.encoders.Base64.decode(encodeBytes);
        System.out.println(new String(decodeBytes));
    }
}
