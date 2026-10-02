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
public class ArrayFill_ESTest_test15 extends ArrayFill_ESTest_scaffolding {

    private static final int SINGLE_ELEMENT_ARRAY_LENGTH = 1;
    private static final int OUT_OF_RANGE_DASH_INDEX = (int) '-';

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        char[] arrayToClear = new char[SINGLE_ELEMENT_ARRAY_LENGTH];

        try {
            ArrayFill.clear(arrayToClear, OUT_OF_RANGE_DASH_INDEX, OUT_OF_RANGE_DASH_INDEX);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("java.util.Arrays", e);
        }
    }
}
