package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test07 extends Base58_ESTest_scaffolding {

    private static final String BASE58_TEXT_WITH_INVALID_PIPE = "U/H~Pu3Wv_(Ozg_|N";

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Base58 decoder = new Base58();

        try {
            decoder.decode(BASE58_TEXT_WITH_INVALID_PIPE);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.codec.binary.Base58", e);
        }
    }
}
