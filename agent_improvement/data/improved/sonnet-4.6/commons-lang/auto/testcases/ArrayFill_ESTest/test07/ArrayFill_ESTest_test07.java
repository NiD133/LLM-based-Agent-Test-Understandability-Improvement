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

    // ArrayFill.fill returns the same array instance (fluent API), not a copy
    @Test(timeout = 4000)
    public void test_fillLongArray_returnsSameArrayInstance() throws Throwable {
        long[] originalArray = new long[2];
        long[] filledArray = ArrayFill.fill(originalArray, 0L);
        assertSame(filledArray, originalArray);
    }
}
