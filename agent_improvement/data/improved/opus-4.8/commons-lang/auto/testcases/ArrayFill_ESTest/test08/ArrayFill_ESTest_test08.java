package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test08 extends ArrayFill_ESTest_scaffolding {

    /**
     * Filling a null {@code long[]} is a no-op: the method returns null
     * rather than throwing, regardless of the fill value.
     */
    @Test(timeout = 4000)
    public void fillNullLongArrayReturnsNull() throws Throwable {
        long[] nullLongArray = null;
        long fillValue = -1L;

        long[] result = ArrayFill.fill(nullLongArray, fillValue);

        assertNull(result);
    }
}
