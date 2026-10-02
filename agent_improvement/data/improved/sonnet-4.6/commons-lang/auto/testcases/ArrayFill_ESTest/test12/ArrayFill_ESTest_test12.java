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
public class ArrayFill_ESTest_test12 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that filling a null float array returns null rather than throwing an exception.
     * ArrayFill.fill is designed to be null-safe and should propagate null input as null output.
     */
    @Test(timeout = 4000)
    public void test_fillNullFloatArray_returnsNull() throws Throwable {
        float[] nullArray = null;
        float fillValue = 0.0F;

        float[] result = ArrayFill.fill(nullArray, fillValue);

        assertNull(result);
    }
}
