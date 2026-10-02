package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test12 extends Weeks_ESTest_scaffolding {

    /**
     * Subtracting zero weeks from zero weeks should leave the amount unchanged.
     * Because {@link Weeks#ZERO} is a cached singleton and the result is also
     * zero weeks, the subtraction returns the very same instance.
     */
    @Test(timeout = 4000)
    public void subtractingZeroWeeksFromZeroReturnsSameZeroInstance() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;

        Weeks result = zeroWeeks.minus((TemporalAmount) zeroWeeks);

        assertSame(zeroWeeks, result);
    }
}
