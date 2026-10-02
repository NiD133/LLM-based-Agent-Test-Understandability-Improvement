package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Random;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockRandom;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Md5Crypt_ESTest_test0 extends Md5Crypt_ESTest_scaffolding {

    private static final int EMPTY_PASSWORD_LENGTH = 9;
    private static final byte RANDOM_SEED = (byte) (-21);
    private static final String INVALID_SALT = "%=XSdI= ";
    private static final String CUSTOM_PREFIX = "1%NP@$";

    @Test(timeout = 4000)
    public void rejectsSaltThatDoesNotMatchMd5CryptPattern() throws Throwable {
        byte[] passwordBytes = new byte[EMPTY_PASSWORD_LENGTH];
        MockRandom deterministicRandom = new MockRandom(RANDOM_SEED);

        try {
            Md5Crypt.md5Crypt(passwordBytes, INVALID_SALT, CUSTOM_PREFIX, (Random) deterministicRandom);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verifyException("org.apache.commons.codec.digest.Md5Crypt", exception);
        }
    }
}
