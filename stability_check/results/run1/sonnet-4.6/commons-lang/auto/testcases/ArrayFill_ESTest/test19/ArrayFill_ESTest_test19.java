package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Arrays;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test19 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that filling a null boolean array returns null without throwing an exception.
     * ArrayFill.fill is documented to accept null arrays and return them as-is.
     */
    @Test(timeout = 4000)
    public void test_fillNullBooleanArray_returnsNull() throws Throwable {
        boolean[] nullArray = null;

        boolean[] result = ArrayFill.fill(nullArray, true);

        assertNull("Filling a null array should return null", result);
    }
}
