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
public class ArrayFill_ESTest_test13 extends ArrayFill_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        double[] doubleArray0 = new double[14];
        double[] doubleArray1 = ArrayFill.fill(doubleArray0, 0.0);
        assertSame(doubleArray1, doubleArray0);
    }
}
