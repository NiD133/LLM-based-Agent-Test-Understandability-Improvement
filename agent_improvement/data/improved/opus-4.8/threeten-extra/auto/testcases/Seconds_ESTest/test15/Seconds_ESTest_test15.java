package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test15 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that the ZERO constant holds an amount of 0 seconds and is
     * therefore not considered positive.
     */
    @Test(timeout = 4000)
    public void zeroSecondsIsNotPositive() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;

        assertFalse("ZERO should not be positive", zeroSeconds.isPositive());
        assertEquals("ZERO should hold an amount of 0", 0, zeroSeconds.getAmount());
    }
}
