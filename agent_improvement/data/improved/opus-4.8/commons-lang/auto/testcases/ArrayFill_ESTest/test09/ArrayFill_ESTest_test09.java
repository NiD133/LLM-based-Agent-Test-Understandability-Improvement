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
public class ArrayFill_ESTest_test09 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that {@link ArrayFill#fill(int[], int)} assigns the given value
     * to every element of the array and returns the same array instance.
     */
    @Test(timeout = 4000)
    public void fillIntArraySetsEveryElementToGivenValue() throws Throwable {
        int[] arrayToFill = new int[4];

        int[] filledArray = ArrayFill.fill(arrayToFill, 1);

        assertArrayEquals(new int[] { 1, 1, 1, 1 }, filledArray);
    }
}
