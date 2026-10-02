package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test21 extends ArrayFill_ESTest_scaffolding {

    /**
     * Clearing a null char array should simply return null instead of throwing.
     */
    @Test(timeout = 4000)
    public void clearNullCharArrayReturnsNull() throws Throwable {
        char[] result = ArrayFill.clear((char[]) null);

        assertNull(result);
    }
}
