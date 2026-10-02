package org.jsoup.helper;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test29 extends Validate_ESTest_scaffolding {

    /**
     * notNullParam should accept a non-null argument without throwing.
     * The parameter name is only used to build the message when validation fails,
     * so passing a non-null object must complete normally.
     */
    @Test(timeout = 4000)
    public void notNullParamWithNonNullArgumentDoesNotThrow() throws Throwable {
        Object nonNullArgument = "yr`o{,Pr'v!D5M";
        String parameterName = "yr`o{,Pr'v!D5M";

        Validate.notNullParam(nonNullArgument, parameterName);
    }
}
