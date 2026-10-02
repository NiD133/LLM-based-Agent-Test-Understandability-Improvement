package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test04 extends Validate_ESTest_scaffolding {

    private static final String EMPTY_VALUE = "";
    private static final String EMPTY_PARAMETER_NAME = "";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        try {
            Validate.notEmptyParam(EMPTY_VALUE, EMPTY_PARAMETER_NAME);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            // The '' parameter must not be empty.
            verifyException("org.jsoup.helper.Validate", exception);
        }
    }
}
