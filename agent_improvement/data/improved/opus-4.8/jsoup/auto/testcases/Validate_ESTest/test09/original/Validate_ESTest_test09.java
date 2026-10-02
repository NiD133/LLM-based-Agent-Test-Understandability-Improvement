package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test09 extends Validate_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Integer integer0 = new Integer((-1));
        Object[] objectArray0 = new Object[5];
        objectArray0[0] = (Object) integer0;
        // Undeclared exception!
        try {
            Validate.noNullElements(objectArray0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Array must not contain any null objects
            //
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
