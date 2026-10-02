package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test19 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that the zero-seconds amount is considered neither negative nor positive.
     */
    @Test(timeout = 4000)
    public void zeroSecondsIsNeitherNegativeNorPositive() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;

        assertFalse("ZERO should not be negative", zeroSeconds.isNegative());
        assertFalse("ZERO should not be positive", zeroSeconds.isPositive());
    }
}
