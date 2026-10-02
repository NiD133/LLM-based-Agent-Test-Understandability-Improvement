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
public class Md5Crypt_ESTest_test4 extends Md5Crypt_ESTest_scaffolding {

    /**
     * Verifies that apr1Crypt produces the expected Apache "$apr1$" hash
     * for a given password and salt.
     */
    @Test(timeout = 4000)
    public void test_apr1Crypt_withPasswordAndSalt_returnsExpectedHash() throws Throwable {
        String password = "~jShJLXp!+";
        String salt = "WU04K";
        String expectedHash = "$apr1$WU04K$bMFrvq41gU5Hz2FhGeKhI.";

        String actualHash = Md5Crypt.apr1Crypt(password, salt);

        assertEquals(expectedHash, actualHash);
    }
}
