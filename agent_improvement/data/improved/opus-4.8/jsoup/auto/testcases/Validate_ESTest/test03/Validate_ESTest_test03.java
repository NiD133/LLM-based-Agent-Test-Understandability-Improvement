package org.jsoup.helper;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test03 extends Validate_ESTest_scaffolding {

    /**
     * Validate.notEmptyParam accepts a non-empty string parameter without
     * throwing. Here both the value being validated and the parameter name
     * are non-empty, so the call completes normally.
     */
    @Test(timeout = 4000)
    public void notEmptyParamAcceptsNonEmptyString() throws Throwable {
        String nonEmptyValue = "Array must not contain any null objects";
        String parameterName = "Array must not contain any null objects";

        Validate.notEmptyParam(nonEmptyValue, parameterName);
    }
}
