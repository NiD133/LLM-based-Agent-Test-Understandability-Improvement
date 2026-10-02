package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test02 extends Validate_ESTest_scaffolding {

    private static final String CUSTOM_ERROR_MESSAGE = "Kw>ry`vUEOCJ/7>w'";

    @Test(timeout = 4000)
    public void notEmpty_withNullString_throwsIllegalArgumentExceptionWithCustomMessage() throws Throwable {
        // Validate.notEmpty(null, msg) must throw IllegalArgumentException
        // using the supplied custom message as the exception detail.
        try {
            Validate.notEmpty((String) null, CUSTOM_ERROR_MESSAGE);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
