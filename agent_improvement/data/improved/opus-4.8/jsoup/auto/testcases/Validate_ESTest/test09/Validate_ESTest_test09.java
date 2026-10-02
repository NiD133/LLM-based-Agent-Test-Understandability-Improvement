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
     * Validate.noNullElements should reject an array that contains any null
     * element. Here the array holds one real value at index 0 and leaves the
     * remaining four slots null, so validation must fail.
     */
    @Test(timeout = 4000)
    public void noNullElements_rejectsArrayContainingNullElement() throws Throwable {
        Object[] arrayWithNulls = new Object[5];
        arrayWithNulls[0] = Integer.valueOf(-1);

        try {
            Validate.noNullElements(arrayWithNulls);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate rejects the null elements with message:
            // "Array must not contain any null objects"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
