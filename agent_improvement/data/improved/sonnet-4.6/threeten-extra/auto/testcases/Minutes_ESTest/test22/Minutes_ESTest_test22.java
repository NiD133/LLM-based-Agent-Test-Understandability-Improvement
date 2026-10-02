package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test22 extends Minutes_ESTest_scaffolding {

    // -946 days × 1440 min/day = -1,362,240 minutes
    // (-946) minus (-1,362,240) = 1,361,294
    private static final int INITIAL_MINUTES_VALUE   = -946;
    private static final int PERIOD_DAYS             = -946;
    private static final int EXPECTED_MINUTES_RESULT = 1361294;

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Minutes initialMinutes  = Minutes.of(INITIAL_MINUTES_VALUE);
        Period  negativeDaysPeriod = Period.ofDays(PERIOD_DAYS);

        // minus(TemporalAmount) converts the Period's days to minutes before subtracting
        Minutes result = initialMinutes.minus((TemporalAmount) negativeDaysPeriod);

        // Minutes is immutable: the original instance must be unchanged
        assertEquals(INITIAL_MINUTES_VALUE, initialMinutes.getAmount());
        // subtracting a large negative minute count yields a large positive result
        assertEquals(EXPECTED_MINUTES_RESULT, result.getAmount());
    }
}
