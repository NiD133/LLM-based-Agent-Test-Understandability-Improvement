package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test02 extends UtcInstant_ESTest_scaffolding {

    // An epoch-second value that falls exactly on a day boundary in the far future (year ~2739877).
    // 86_400_000_000_000L / 86_400 == 1_000_000_000 days from 1970-01-01.
    private static final long EPOCH_SECOND_AT_FAR_FUTURE_DAY_BOUNDARY = 86_400_000_000_000L;

    // Subtracting 3 nanoseconds places the instant 3 ns before midnight,
    // i.e. at 23:59:59.999999997 on the day before the boundary.
    private static final long NANOS_BEFORE_MIDNIGHT = 3L;

    @Test(timeout = 4000)
    public void test_toString_nearDayBoundaryInFarFuture_returnsExpectedIsoString() throws Throwable {
        Instant dayBoundary = MockInstant.ofEpochSecond(EPOCH_SECOND_AT_FAR_FUTURE_DAY_BOUNDARY);
        Instant threeNanosBeforeMidnight = MockInstant.minusNanos(dayBoundary, NANOS_BEFORE_MIDNIGHT);

        UtcInstant utcInstant = UtcInstant.of(threeNanosBeforeMidnight);
        String result = utcInstant.toString();

        assertNotNull(result);
        assertEquals("+2739877-01-02T23:59:59.999999997Z", result);
    }
}
