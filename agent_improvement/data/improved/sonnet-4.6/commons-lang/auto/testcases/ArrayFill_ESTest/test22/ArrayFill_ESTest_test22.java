package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test22 extends ArrayFill_ESTest_scaffolding {

    // ArrayFill.clear(byte[]) should propagate null input back as null output
    @Test(timeout = 4000)
    public void test_clearByteArray_withNullInput_returnsNull() throws Throwable {
        byte[] result = ArrayFill.clear((byte[]) null);
        assertNull(result);
    }
}
