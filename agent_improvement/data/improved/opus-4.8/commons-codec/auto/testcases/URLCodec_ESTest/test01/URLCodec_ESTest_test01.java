package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test01 extends URLCodec_ESTest_scaffolding {

    /**
     * A trailing escape character ('%') that is not followed by the two
     * required hexadecimal digits is malformed. Decoding such input must fail,
     * because {@link URLCodec#decodeUrl(byte[])} reads past the end of the
     * array and reports the resulting error as an "Invalid URL encoding".
     */
    @Test(timeout = 4000)
    public void decodeUrl_withTrailingEscapeChar_throwsException() throws Throwable {
        final byte ESCAPE_CHAR = (byte) '%'; // 37
        byte[] inputEndingWithEscapeChar = new byte[5];
        inputEndingWithEscapeChar[4] = ESCAPE_CHAR;

        try {
            URLCodec.decodeUrl(inputEndingWithEscapeChar);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Invalid URL encoding: the '%' has no following hex digits.
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
