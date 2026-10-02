package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test23 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtils#newString(byte[], String)} wraps the
     * underlying {@link java.io.UnsupportedEncodingException} in an
     * {@link IllegalStateException} when the supplied charset name is not a
     * valid/supported encoding.
     */
    @Test(timeout = 4000)
    public void newStringWithUnsupportedCharsetNameThrowsIllegalStateException() throws Throwable {
        byte[] bytesToDecode = new byte[4];
        // Not the name of any supported charset: U+00DA followed by a NUL character.
        String unsupportedCharsetName = "\u00DA\u0000";

        try {
            StringUtils.newString(bytesToDecode, unsupportedCharsetName);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // The unsupported encoding is reported as an IllegalStateException
            // thrown from StringUtils, wrapping an UnsupportedEncodingException.
            verifyException("org.apache.commons.codec.binary.StringUtils", e);
        }
    }
}
