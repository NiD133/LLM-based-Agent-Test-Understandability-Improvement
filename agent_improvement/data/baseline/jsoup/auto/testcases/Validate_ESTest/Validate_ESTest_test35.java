package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test35 extends Validate_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test35() throws Throwable {
        // Undeclared exception!
        try {
            Validate.wtf((String) null);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
