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
     * Verifies that getBytesUnchecked throws IllegalStateException when given
     * an unrecognized (invalid) charset name, wrapping the underlying
     * UnsupportedEncodingException.
     */
    @Test(timeout = 4000)
    public void test03_getBytesUnchecked_throwsIllegalStateException_forInvalidCharsetName() throws Throwable {
        String inputString = "";
        String invalidCharsetName = "(![FyY^jueCk Sf|!,w";

        try {
            StringUtils.getBytesUnchecked(inputString, invalidCharsetName);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.codec.binary.StringUtils", e);
        }
    }
}
