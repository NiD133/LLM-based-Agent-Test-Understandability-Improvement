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
public class ArrayFill_ESTest_test10 extends ArrayFill_ESTest_scaffolding {

    // ArrayFill.fill is null-safe: passing a null array should return null unchanged.
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        int[] result = ArrayFill.fill((int[]) null, 0);
        assertNull(result);
    }
}
