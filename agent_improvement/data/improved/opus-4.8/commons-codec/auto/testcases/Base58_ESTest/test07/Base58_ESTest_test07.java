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

    /**
     * Decoding a string that contains characters outside the Base58 alphabet must fail.
     * Here the input includes the pipe character '|' (0x7c), which is not a valid
     * Base58 digit, so {@link Base58#decode(String)} is expected to throw an
     * IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void decodeRejectsStringWithInvalidBase58Character() throws Throwable {
        Base58 base58 = new Base58();
        String inputWithInvalidCharacter = "U/H~Pu3Wv_(Ozg_|N";

        try {
            base58.decode(inputWithInvalidCharacter);
            fail("Expected IllegalArgumentException for invalid Base58 character 0x7c ('|')");
        } catch (IllegalArgumentException e) {
            // Message: "Invalid character in Base58 string: 0x7c"
            verifyException("org.apache.commons.codec.binary.Base58", e);
        }
    }
}
