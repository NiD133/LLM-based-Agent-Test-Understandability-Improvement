package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test15 extends ArrayFill_ESTest_scaffolding {

    // ASCII value of '-' is 45, which is far beyond the valid index range of a 1-element array
    private static final int OUT_OF_BOUNDS_INDEX = (int) '-';

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        char[] singleElementArray = new char[1];

        try {
            ArrayFill.clear(singleElementArray, OUT_OF_BOUNDS_INDEX, OUT_OF_BOUNDS_INDEX);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("java.util.Arrays", e);
        }
    }
}
