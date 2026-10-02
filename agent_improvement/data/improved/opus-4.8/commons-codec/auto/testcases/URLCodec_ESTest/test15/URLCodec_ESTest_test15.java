package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test15 extends URLCodec_ESTest_scaffolding {

    /**
     * Decoding a string whose escape sequence is malformed must fail.
     * In "*aAC%+" the '%' starts an escape that should be followed by two
     * hexadecimal digits, but the next character is '+' (ASCII 43), which is
     * not a valid radix-16 digit. The decode therefore aborts inside
     * {@code Utils.digit16(...)}.
     */
    @Test(timeout = 4000)
    public void decodeWithNonHexDigitAfterEscapeThrows() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        try {
            urlCodec.decode((Object) "*aAC%+");
            fail("Expected an exception for the invalid escape sequence '%+'");
        } catch (Exception e) {
            // Invalid URL encoding: not a valid digit (radix 16): 43
            verifyException("org.apache.commons.codec.net.Utils", e);
        }
    }
}
