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
     * getBytesUnchecked wraps the JDK's UnsupportedEncodingException in an
     * IllegalStateException when given a charset name that does not exist.
     */
    @Test(timeout = 4000)
    public void getBytesUncheckedWithUnknownCharsetThrowsIllegalState() throws Throwable {
        String anyString = "";
        String unknownCharsetName = "(![FyY^jueCk Sf|!,w";

        try {
            StringUtils.getBytesUnchecked(anyString, unknownCharsetName);
            fail("Expected IllegalStateException for unknown charset name: " + unknownCharsetName);
        } catch (IllegalStateException expected) {
            // The unsupported encoding is reported by StringUtils itself.
            verifyException("org.apache.commons.codec.binary.StringUtils", expected);
        }
    }
}
