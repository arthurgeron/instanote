package infra;

import sun.plugin2.message.Message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by hellguy on 30/09/17.
 */
public class Security {
    public static String GenerateMD5() throws NoSuchAlgorithmException {
        int size = 20;
        SecureRandom rnd = new SecureRandom();
        byte[] token = new byte[size];
        rnd.nextBytes(token);
        return GenerateMD5(rnd.toString());
    }

    public static String GenerateMD5(String input) throws NoSuchAlgorithmException {
        String saltedInput = input + Credentials.getSalt();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(saltedInput.getBytes());
        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public static String GenerateToken() throws NoSuchAlgorithmException {
        return GenerateMD5();
    }
}
