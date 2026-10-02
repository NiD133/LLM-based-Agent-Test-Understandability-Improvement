package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test15 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void isPositive_returnsFalse_whenSecondsIsZero() throws Throwable {
        Seconds zero = Seconds.ZERO;

        boolean result = zero.isPositive();

        assertFalse("Seconds.ZERO should not be positive", result);
        assertEquals("Seconds.ZERO should have amount of 0", 0, zero.getAmount());
    }
}
