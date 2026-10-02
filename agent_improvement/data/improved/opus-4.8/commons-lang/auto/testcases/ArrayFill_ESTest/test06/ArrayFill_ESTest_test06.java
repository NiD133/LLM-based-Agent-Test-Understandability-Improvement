package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test06 extends ArrayFill_ESTest_scaffolding {

    /**
     * Filling a null short array is a no-op: the method returns null
     * rather than throwing a NullPointerException.
     */
    @Test(timeout = 4000)
    public void fillNullShortArrayReturnsNull() throws Throwable {
        short[] nullShortArray = null;
        short fillValue = (short) -807;

        short[] result = ArrayFill.fill(nullShortArray, fillValue);

        assertNull(result);
    }
}
