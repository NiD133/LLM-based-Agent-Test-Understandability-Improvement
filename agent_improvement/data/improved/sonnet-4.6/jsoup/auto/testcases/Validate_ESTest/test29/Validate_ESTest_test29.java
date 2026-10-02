package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test29 extends Validate_ESTest_scaffolding {

    // Verifies that notNullParam does not throw when the supplied object is non-null.
    @Test(timeout = 4000)
    public void test29() throws Throwable {
        String nonNullValue = "yr`o{,Pr'v!D5M";
        String paramName    = "yr`o{,Pr'v!D5M";
        Validate.notNullParam(nonNullValue, paramName);
    }
}
