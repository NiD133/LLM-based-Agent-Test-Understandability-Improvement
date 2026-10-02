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
public class ArrayFill_ESTest_test17 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that {@link ArrayFill#clear(byte[])} resets every element of a
     * byte array to {@code 0} and returns that same array.
     */
    @Test(timeout = 4000)
    public void clear_byteArray_setsAllElementsToZero() throws Throwable {
        byte[] singleElementArray = new byte[1];

        byte[] clearedArray = ArrayFill.clear(singleElementArray);

        assertArrayEquals(new byte[] { (byte) 0 }, clearedArray);
    }
}
