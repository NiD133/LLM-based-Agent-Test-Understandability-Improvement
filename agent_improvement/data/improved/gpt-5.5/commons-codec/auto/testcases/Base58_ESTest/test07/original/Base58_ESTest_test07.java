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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Base58 base58_0 = new Base58();
        // Undeclared exception!
        try {
            base58_0.decode("U/H~Pu3Wv_(Ozg_|N");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Invalid character in Base58 string: 0x7c
            //
            verifyException("org.apache.commons.codec.binary.Base58", e);
        }
    }
}
