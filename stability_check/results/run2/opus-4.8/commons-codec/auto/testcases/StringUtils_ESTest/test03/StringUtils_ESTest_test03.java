package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test03 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtils#getBytesUnchecked(String, String)} rethrows the
     * {@link java.io.UnsupportedEncodingException} raised for an unknown charset name as an
     * {@link IllegalStateException}.
     */
    @Test(timeout = 4000)
    public void getBytesUncheckedWithUnknownCharsetThrowsIllegalState() throws Throwable {
        String anyString = "";
        String unknownCharsetName = "(![FyY^jueCk Sf|!,w";

        try {
            StringUtils.getBytesUnchecked(anyString, unknownCharsetName);
            fail("Expected IllegalStateException for unknown charset name: " + unknownCharsetName);
        } catch (IllegalStateException e) {
            // The unsupported encoding is wrapped and rethrown from StringUtils.
            verifyException("org.apache.commons.codec.binary.StringUtils", e);
        }
    }
}
