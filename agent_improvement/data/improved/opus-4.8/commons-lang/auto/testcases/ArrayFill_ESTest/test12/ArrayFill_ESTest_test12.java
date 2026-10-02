package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test12 extends ArrayFill_ESTest_scaffolding {

    /**
     * Filling a null float array is a no-op: the method should simply
     * return null instead of throwing a NullPointerException.
     */
    @Test(timeout = 4000)
    public void fillNullFloatArrayReturnsNull() throws Throwable {
        float[] result = ArrayFill.fill((float[]) null, 0.0F);

        assertNull(result);
    }
}
