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

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Undeclared exception!
        try {
            Validate.notEmpty((String) null, "Kw>ry`vUEOCJ/7>w'");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Kw>ry`vUEOCJ/7>w'
            //
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
