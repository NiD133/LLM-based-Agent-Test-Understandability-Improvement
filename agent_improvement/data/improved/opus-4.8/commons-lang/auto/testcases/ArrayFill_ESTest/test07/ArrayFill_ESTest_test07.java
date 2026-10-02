package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test07 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that {@link ArrayFill#fill(long[], long)} fills the array in place
     * and returns the very same array instance that was passed in (fluent style).
     */
    @Test(timeout = 4000)
    public void fillLongArrayReturnsSameInstance() throws Throwable {
        long[] inputArray = new long[2];

        long[] returnedArray = ArrayFill.fill(inputArray, 0L);

        assertSame(inputArray, returnedArray);
    }
}
