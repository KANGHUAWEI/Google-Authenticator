package test;

import cn.limbo.utils.GoogleAuthenticator;
import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by limbo on 2016/11/25.
 */
public class GoogleAuthenticatorTest {

    static String savedSecret = "F6EUJJMYK7GDC4KI";

    @Test
    public void genSecretTest() {
        try {
            String secret = GoogleAuthenticator.generateSecretKey();
            String url = GoogleAuthenticator.getQRBarcodeURL("testuser", "testhost", secret);
            System.out.println("Please register " + url);
            System.out.println("Secret key is " + secret);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void authTest(){
        long code = 218707;
        long t = System.currentTimeMillis();
        GoogleAuthenticator ga = new GoogleAuthenticator();
        ga.setWindowSize(15);

        try {
           boolean r = ga.checkCode(savedSecret,code,t);
            System.out.println("Check code = " + r);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

}
