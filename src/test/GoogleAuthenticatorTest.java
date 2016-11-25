package test;

import cn.limbo.utils.GoogleAuthenticator;
import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by limbo on 2016/11/25.
 */
public class GoogleAuthenticatorTest {


    @Test
    public void genSecret(){
        System.out.println(GoogleAuthenticator.generateSecret());
    }

    @Test
    public void authCode(){
        System.out.println(GoogleAuthenticator.authCode("299385","EHOHGT76B5XS7P5G"));
    }

}
