package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test10 extends ArrayFill_ESTest_scaffolding {

    /**
     * Filling a null int[] is a no-op: the method returns the same null
     * reference instead of throwing a NullPointerException.
     */
    @Test(timeout = 4000)
    public void fillNullIntArrayReturnsNull() throws Throwable {
        int fillValue = 0;
        int[] result = ArrayFill.fill((int[]) null, fillValue);

        assertNull(result);
    }
}
