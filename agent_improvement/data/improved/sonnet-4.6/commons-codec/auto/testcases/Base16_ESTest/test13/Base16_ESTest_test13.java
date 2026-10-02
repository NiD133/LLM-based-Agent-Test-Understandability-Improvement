package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test13 extends Base16_ESTest_scaffolding {

    // ASCII newline ('\n') has byte value 10, which is outside the Base16 alphabet (0-9, A-F / a-f)
    private static final byte ASCII_NEWLINE = (byte) 10;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // uppercase=false means we use the uppercase Base16 alphabet (digits 0-9 and letters A-F)
        Base16 uppercaseBase16 = new Base16(false);

        boolean isNewlineInAlphabet = uppercaseBase16.isInAlphabet(ASCII_NEWLINE);

        assertFalse("Newline byte (\\n, value 10) must not be recognized as a valid Base16 character",
                isNewlineInAlphabet);
    }
}
