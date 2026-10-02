package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test16 extends URLCodec_ESTest_scaffolding {

    /**
     * Decoding fails when the codec is configured with a charset name that is not a
     * valid/supported charset. {@code decode(Object)} dispatches the String to
     * {@code decode(String)}, which tries to build the result using the configured
     * charset; an unsupported charset triggers an exception from inside URLCodec.
     */
    @Test(timeout = 4000)
    public void decodeWithUnsupportedCharsetThrowsException() throws Throwable {
        String unsupportedCharsetName = "~zP+pe;V}>f#Rj";
        URLCodec codecWithBadCharset = new URLCodec(unsupportedCharsetName);

        try {
            codecWithBadCharset.decode((Object) "~zP+pe;V}>f#Rj");
            fail("Expected an exception because the configured charset is not supported");
        } catch (Exception e) {
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
