package com.instanote;

import com.instanote.infra.Security;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * Created by hellguy on 30/09/17.
 */
public class SecurityTest {

    @Test
    public void CanEncrypt() {
        String samplePassword= "comple$Pa$$word&£^52";

        try {
            Assert.assertEquals(Security.GenerateMD5(samplePassword), Security.GenerateMD5(samplePassword));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void CanGenerateMD5() {
        try {
            Assert.assertNotEquals(Security.GenerateMD5(), Security.GenerateMD5());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void CanGenerateToken() {
        try {
            String token = Security.GenerateToken();
            Assert.assertNotNull(token);
            Assert.assertNotEquals(token, Security.GenerateMD5());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
