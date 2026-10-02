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
public class ArrayFill_ESTest_test05 extends ArrayFill_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05_fillShortArray_allElementsSetToGivenValue() throws Throwable {
        final short fillValue = (short) 2260;
        short[] input = new short[2];

        short[] result = ArrayFill.fill(input, fillValue);

        assertArrayEquals(
            "fill() should set every element to the given short value",
            new short[] { fillValue, fillValue },
            result
        );
    }
}
