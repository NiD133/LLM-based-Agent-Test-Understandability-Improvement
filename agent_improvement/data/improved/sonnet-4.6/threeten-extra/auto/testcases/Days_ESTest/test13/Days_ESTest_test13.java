package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test13 extends Days_ESTest_scaffolding {

    /**
     * Subtracting a zero Duration from Days.ONE should leave the value unchanged
     * and return the exact same Days instance (since no arithmetic is needed).
     */
    @Test(timeout = 4000)
    public void subtractingZeroDurationFromOneDayReturnsSameInstance() throws Throwable {
        Days oneDay = Days.ONE;
        Duration zeroDuration = Duration.ZERO;

        Days result = oneDay.minus(zeroDuration);

        assertFalse("Result of subtracting zero should not be negative", result.isNegative());
        assertSame("Subtracting zero should return the same Days instance", result, oneDay);
    }
}
