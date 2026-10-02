package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test28 extends Validate_ESTest_scaffolding {

    /**
     * notNullParam should reject a null parameter value by throwing an
     * IllegalArgumentException (ValidationException), even when the parameter
     * name is also null.
     */
    @Test(timeout = 4000)
    public void notNullParam_withNullValue_throwsIllegalArgumentException() throws Throwable {
        Object nullValue = null;
        String nullParamName = null;

        try {
            Validate.notNullParam(nullValue, nullParamName);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "The parameter 'null' must not be null."
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
