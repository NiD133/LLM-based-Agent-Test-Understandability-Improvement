package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test20 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that clearing a range of a null char array is a no-op:
     * the method tolerates a null array and simply returns null.
     */
    @Test(timeout = 4000)
    public void clearRangeOfNullCharArrayReturnsNull() throws Throwable {
        char[] nullCharArray = null;
        int fromIndex = 'f';
        int toIndex = 'f';

        char[] result = ArrayFill.clear(nullCharArray, fromIndex, toIndex);

        assertNull(result);
    }
}
