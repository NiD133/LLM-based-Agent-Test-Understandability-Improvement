package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test09 extends UtcInstant_ESTest_scaffolding {

    private static final long MJD_DAY_13 = 13L;
    // 13 nanoseconds after midnight — effectively the very start of the day
    private static final long NEAR_START_OF_DAY_NANOS = 13L;
    // ~86398.161 seconds into the day — roughly 23:59:58, near end of day
    private static final long NEAR_END_OF_DAY_NANOS = 86398161000000L;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Two instants on the same MJD day: one near the start, one near the end
        UtcInstant earlyInDay = UtcInstant.ofModifiedJulianDay(MJD_DAY_13, NEAR_START_OF_DAY_NANOS);
        UtcInstant lateInDay  = UtcInstant.ofModifiedJulianDay(MJD_DAY_13, NEAR_END_OF_DAY_NANOS);

        boolean earlyIsBeforeLate = earlyInDay.isBefore(lateInDay);

        // Both instants share the same Modified Julian Day
        assertEquals(MJD_DAY_13, lateInDay.getModifiedJulianDay());
        // An instant near the start of the day must be before one near the end
        assertTrue(earlyIsBeforeLate);
    }
}
