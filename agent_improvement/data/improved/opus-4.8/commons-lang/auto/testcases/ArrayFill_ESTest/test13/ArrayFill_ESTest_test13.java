package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test13 extends ArrayFill_ESTest_scaffolding {

    /**
     * Verifies that filling a double array returns the very same array
     * instance that was passed in (fluent style), not a copy.
     */
    @Test(timeout = 4000)
    public void fillDoubleArrayReturnsSameInstance() throws Throwable {
        double[] arrayToFill = new double[14];

        double[] filledArray = ArrayFill.fill(arrayToFill, 0.0);

        assertSame(arrayToFill, filledArray);
    }
}
