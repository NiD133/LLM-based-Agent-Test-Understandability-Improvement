package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test13 extends Days_ESTest_scaffolding {

    /**
     * Subtracting a zero-length amount from {@code Days.ONE} should leave the value
     * unchanged. Because subtracting zero is a no-op, the method returns the very
     * same instance, and the (positive) result is therefore not negative.
     */
    @Test(timeout = 4000)
    public void subtractingZeroDurationReturnsSameInstance() throws Throwable {
        Days one = Days.ONE;
        TemporalAmount zeroDuration = Duration.ZERO;

        Days result = one.minus(zeroDuration);

        assertFalse("subtracting zero keeps the amount positive", result.isNegative());
        assertSame("subtracting zero should return the original instance", one, result);
    }
}
