package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test19 extends ArrayFill_ESTest_scaffolding {

    /**
     * Filling a null boolean array returns null: the method tolerates a null
     * input and simply hands it back unchanged.
     */
    @Test(timeout = 4000)
    public void fillNullBooleanArrayReturnsNull() throws Throwable {
        boolean[] result = ArrayFill.fill((boolean[]) null, true);

        assertNull(result);
    }
}
