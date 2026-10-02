package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test28 extends Validate_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        Object value = null;
        String parameterName = null;

        try {
            Validate.notNullParam(value, parameterName);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expectedException) {
            verifyException("org.jsoup.helper.Validate", expectedException);
        }
    }
}
