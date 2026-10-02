package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test32 extends Validate_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test32() throws Throwable {
        // Undeclared exception!
        try {
            Validate.fail("org.jsoup.helper.ValidationException", (Object[]) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // org.jsoup.helper.ValidationException
            //
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
