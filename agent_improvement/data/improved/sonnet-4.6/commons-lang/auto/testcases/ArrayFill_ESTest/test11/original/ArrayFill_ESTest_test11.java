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
public class ArrayFill_ESTest_test11 extends ArrayFill_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        float[] floatArray0 = new float[1];
        float[] floatArray1 = ArrayFill.fill(floatArray0, 0.0F);
        assertArrayEquals(new float[] { 0.0F }, floatArray1, 0.01F);
    }
}
