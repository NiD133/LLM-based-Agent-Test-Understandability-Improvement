package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test09 extends Minutes_ESTest_scaffolding {

    /**
     * Dividing zero minutes by any divisor yields zero minutes. Since the
     * result is zero, {@code dividedBy} returns the shared {@link Minutes#ZERO}
     * singleton, so the result is the very same instance we started from.
     */
    @Test(timeout = 4000)
    public void dividingZeroMinutesReturnsTheZeroSingleton() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;

        Minutes result = zeroMinutes.dividedBy(-8);

        assertSame(zeroMinutes, result);
    }
}
