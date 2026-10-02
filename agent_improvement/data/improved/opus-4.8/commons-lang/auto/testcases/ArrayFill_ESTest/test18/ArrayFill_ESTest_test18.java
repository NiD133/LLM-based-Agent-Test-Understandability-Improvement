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
public class ArrayFill_ESTest_test18 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that {@link ArrayFill#fill(boolean[], boolean)} sets every element
     * of the array to the given boolean value and returns the same array instance.
     */
    @Test(timeout = 4000)
    public void fillBooleanArrayWithTrue_setsAllElementsToTrue() throws Throwable {
        boolean[] arrayToFill = new boolean[1];

        boolean[] filledArray = ArrayFill.fill(arrayToFill, true);

        boolean[] expectedArray = { true };
        assertTrue(Arrays.equals(expectedArray, filledArray));
    }
}
