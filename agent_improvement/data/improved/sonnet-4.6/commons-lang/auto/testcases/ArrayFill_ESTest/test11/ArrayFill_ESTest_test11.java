package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test11 extends ArrayFill_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_fillFloatArrayWithZero_returnsSameArrayFilledWithZero() throws Throwable {
        float[] input = new float[1];
        float[] result = ArrayFill.fill(input, 0.0F);
        assertArrayEquals(new float[] { 0.0F }, result, 0.01F);
    }
}
