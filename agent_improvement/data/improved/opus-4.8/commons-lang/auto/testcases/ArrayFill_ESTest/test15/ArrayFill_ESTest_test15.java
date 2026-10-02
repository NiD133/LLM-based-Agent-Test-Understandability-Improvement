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

    /**
     * Verifies that {@link ArrayFill#clear(char[], int, int)} rejects index bounds
     * that fall outside the array. The array has a single element (valid indices
     * are 0..1), but the requested range starts at index 45 (the int value of '-'),
     * so the underlying {@link java.util.Arrays#fill} throws.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        char[] singleElementArray = new char[1];
        int outOfBoundsIndex = (int) '-'; // 45, far beyond the array's only valid index

        try {
            ArrayFill.clear(singleElementArray, outOfBoundsIndex, outOfBoundsIndex);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Array index out of range: 45
            verifyException("java.util.Arrays", e);
        }
    }
}
