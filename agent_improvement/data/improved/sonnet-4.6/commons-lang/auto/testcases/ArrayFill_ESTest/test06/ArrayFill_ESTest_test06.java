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
public class ArrayFill_ESTest_test06 extends ArrayFill_ESTest_scaffolding {

    // Any fill value is valid here; the null-safety contract means the array is returned as-is (null)
    private static final short FILL_VALUE = (short) (-807);

    @Test(timeout = 4000)
    public void test06_fillNullShortArray_returnsNull() throws Throwable {
        // ArrayFill.fill documents that a null input array is returned unchanged (i.e. null)
        short[] result = ArrayFill.fill((short[]) null, FILL_VALUE);
        assertNull("fill on a null short[] should return null", result);
    }
}
