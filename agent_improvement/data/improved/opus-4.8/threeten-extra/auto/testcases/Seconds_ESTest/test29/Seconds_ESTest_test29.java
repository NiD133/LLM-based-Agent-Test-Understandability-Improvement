package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test29 extends Seconds_ESTest_scaffolding {

    /**
     * Comparing the ZERO constant to itself should report equality,
     * so compareTo returns 0.
     */
    @Test(timeout = 4000)
    public void compareToReturnsZeroWhenComparingZeroToItself() throws Throwable {
        Seconds zero = Seconds.ZERO;

        int comparison = zero.compareTo(zero);

        assertEquals(0, comparison);
    }
}
