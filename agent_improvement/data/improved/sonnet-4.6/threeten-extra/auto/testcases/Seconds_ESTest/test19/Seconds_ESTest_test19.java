package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test19 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that Seconds.ZERO is neither negative nor positive.
     * Zero seconds has no sign, so both isNegative() and isPositive() must return false.
     */
    @Test(timeout = 4000)
    public void test_zeroSeconds_isNeitherNegativeNorPositive() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;

        boolean isNegative = zeroSeconds.isNegative();
        assertFalse("Seconds.ZERO should not be negative", isNegative);
        assertFalse("Seconds.ZERO should not be positive", zeroSeconds.isPositive());
    }
}
