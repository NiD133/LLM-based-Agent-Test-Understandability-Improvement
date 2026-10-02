package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test03 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtils#getBytesUnchecked(String, String)} rejects an
     * unknown charset name by wrapping the underlying
     * {@link java.io.UnsupportedEncodingException} in an {@link IllegalStateException}.
     */
    @Test(timeout = 4000)
    public void getBytesUncheckedWithUnknownCharsetThrowsIllegalState() throws Throwable {
        String anyText = "";
        String unknownCharsetName = "(![FyY^jueCk Sf|!,w";

        try {
            StringUtils.getBytesUnchecked(anyText, unknownCharsetName);
            fail("Expected an IllegalStateException because the charset name is not supported");
        } catch (IllegalStateException e) {
            // The exception must originate from StringUtils, which converts the
            // UnsupportedEncodingException into an IllegalStateException.
            verifyException("org.apache.commons.codec.binary.StringUtils", e);
        }
    }
}
