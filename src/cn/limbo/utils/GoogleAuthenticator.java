package cn.limbo.utils;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by limbo on 2016/11/25.
 */
public class GoogleAuthenticator {

    public static final int SECRET_SIZE = 10;

    public static final String SEED = "g8GjEvTbW5oVSV7avLBdwIHqGlUYNzKFI7izOF8GwLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";

    public static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";

    int windowSize = 3; //最多可偏移的时间

    public void setWindowSize(int s) {
        if (s >= 1 && s <= 17) {
            windowSize = s;
        }
    }

    public static String generateSecretKey() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = null;

        secureRandom = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
        secureRandom.setSeed(Base64.decodeBase64(SEED));
        byte[] buffer = secureRandom.generateSeed(SECRET_SIZE);
        Base32 codec = new Base32();
        byte[] bEncodedKey = codec.encode(buffer);
        return new String(bEncodedKey);
    }

    public static String getQRBarcodeURL(String user, String host, String secret) {
        String format = "https://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s";
        return String.format(format, user, host, secret);
    }

    public boolean checkCode(String secret, long code, long timeMsec) throws InvalidKeyException, NoSuchAlgorithmException {
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secret);
        long t = (timeMsec / 1000L) / 30L;

        for (int i = -windowSize; i <= windowSize; i++) {
            long hash;
            hash = verifyCode(decodedKey, t + i);
            if (hash == code) {
                return true;
            }
        }
        return false;
    }

    public static int verifyCode(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }

        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = 0; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }
}
