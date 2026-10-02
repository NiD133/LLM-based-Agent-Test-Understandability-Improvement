package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test00 extends URLCodec_ESTest_scaffolding {

    /**
     * Encoding a String relies on the codec's default charset to turn the text
     * into bytes. When that charset name is not a valid/supported charset,
     * encoding must fail with an EncoderException raised by URLCodec.
     */
    @Test(timeout = 4000)
    public void encodeStringWithUnsupportedCharsetThrowsEncoderException() throws Throwable {
        // "Invalid URL encoding: " is not a real charset name; it is used here
        // purely as an unsupported default charset for the codec.
        String unsupportedCharsetName = "Invalid URL encoding: ";
        URLCodec urlCodec = new URLCodec(unsupportedCharsetName);

        try {
            urlCodec.encode((Object) "Invalid URL encoding: ");
            fail("Expected an EncoderException because the default charset is unsupported");
        } catch (Exception e) {
            // The exception originates from URLCodec while encoding the String.
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
