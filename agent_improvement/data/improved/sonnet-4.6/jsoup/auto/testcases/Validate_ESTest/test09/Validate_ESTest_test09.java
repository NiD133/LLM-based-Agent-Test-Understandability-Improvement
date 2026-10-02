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

    /**
     * Verifies that noNullElements throws IllegalArgumentException when an array
     * contains null elements (only index 0 is populated; indices 1–4 remain null).
     */
    @Test(timeout = 4000)
    public void test09_noNullElements_throwsOnArrayWithNullElements() throws Throwable {
        Object[] arrayWithNulls = new Object[5];
        arrayWithNulls[0] = Integer.valueOf(-1);

        try {
            Validate.noNullElements(arrayWithNulls);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Array must not contain any null objects
            //
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
