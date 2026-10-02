package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test05 extends Validate_ESTest_scaffolding {

    /**
     * When both the string value and the parameter name are null,
     * notEmptyParam should throw IllegalArgumentException whose message
     * substitutes the literal text "null" for the missing parameter name:
     * "The 'null' parameter must not be empty."
     */
    @Test(timeout = 4000)
    public void test05_notEmptyParam_nullStringAndNullParamName_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notEmptyParam((String) null, (String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "The 'null' parameter must not be empty."
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
