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
public class ArrayFill_ESTest_test07 extends ArrayFill_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        long[] longArray0 = new long[2];
        long[] longArray1 = ArrayFill.fill(longArray0, 0L);
        assertSame(longArray1, longArray0);
    }
}
