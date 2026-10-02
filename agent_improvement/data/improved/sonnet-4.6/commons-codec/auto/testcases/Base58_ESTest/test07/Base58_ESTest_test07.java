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

    // The pipe character '|' (0x7c) is not part of the Base58 alphabet
    private static final String INPUT_WITH_PIPE_CHARACTER = "U/H~Pu3Wv_(Ozg_|N";

    @Test(timeout = 4000)
    public void test07_decodeStringContainingPipeCharacter_throwsIllegalArgumentException() throws Throwable {
        Base58 base58 = new Base58();
        try {
            base58.decode(INPUT_WITH_PIPE_CHARACTER);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // '|' is 0x7c, which lies outside the Base58 alphabet and triggers this exception
            verifyException("org.apache.commons.codec.binary.Base58", e);
        }
    }
}
