package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test06 extends Base16_ESTest_scaffolding {

    /**
     * Base16 (hex) encoding only allows characters 0-9 and A-F.
     * The input "E5SrU{4M(ZcJi?^<" contains 'S' (ASCII 83), which is not a valid
     * Base16 character. Decoding must throw IllegalArgumentException identifying
     * the offending byte value.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Base16 base16 = new Base16();
        String inputWithInvalidHexCharacter = "E5SrU{4M(ZcJi?^<";

        try {
            base16.decode(inputWithInvalidHexCharacter);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // 'S' has ASCII value 83, which is not in the Base16 alphabet
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
