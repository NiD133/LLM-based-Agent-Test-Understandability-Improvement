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
public class Base16_ESTest_test13 extends Base16_ESTest_scaffolding {

    /**
     * The byte 10 (line-feed) is not one of the Base16 alphabet characters
     * (0-9 and A-F), so {@link Base16#isInAlphabet(byte)} must report it as
     * being outside the alphabet.
     */
    @Test(timeout = 4000)
    public void isInAlphabet_returnsFalse_forNonAlphabetByte() throws Throwable {
        final Base16 base16 = new Base16(false);

        final boolean inAlphabet = base16.isInAlphabet((byte) 10);

        assertFalse("byte 10 is not part of the Base16 alphabet", inAlphabet);
    }
}
