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
     * Decoding a string that contains characters outside the Base16 alphabet
     * (for example 'S', whose byte value is 83) must fail with an
     * IllegalArgumentException reporting the invalid octet.
     */
    @Test(timeout = 4000)
    public void decodeRejectsNonAlphabetCharacter() throws Throwable {
        Base16 base16 = new Base16();
        String inputWithInvalidCharacters = "E5SrU{4M(ZcJi?^<";

        try {
            base16.decode(inputWithInvalidCharacters);
            fail("Expected IllegalArgumentException for invalid octet 'S' (value 83)");
        } catch (IllegalArgumentException e) {
            // Message reads: "Invalid octet in encoded value: 83"
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
