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
public class ArrayFill_ESTest_test02 extends ArrayFill_ESTest_scaffolding {

    // When the generator function is null, fill() skips element assignment and
    // returns the original array unchanged (same reference, not a copy).
    @Test(timeout = 4000)
    public void test_fill_withNullGenerator_returnsSameArrayInstance() throws Throwable {
        Object[] inputArray = new Object[2];
        Object[] resultArray = ArrayFill.fill(inputArray, (FailableIntFunction<?, Throwable>) null);
        assertSame(resultArray, inputArray);
    }
}
