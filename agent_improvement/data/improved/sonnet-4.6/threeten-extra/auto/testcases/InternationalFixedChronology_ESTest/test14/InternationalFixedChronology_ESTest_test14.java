package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test14 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the range for ALIGNED_DAY_OF_WEEK_IN_YEAR returns
     * a non-null ValueRange from the International Fixed chronology.
     *
     * In the IFC calendar, day-of-week-in-year can be 0 (for year-day/leap-day
     * which are not part of any week) or 1–7 for normal days.
     */
    @Test(timeout = 4000)
    public void test_range_alignedDayOfWeekInYear_isNotNull() throws Throwable {
        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;
        ValueRange alignedDayOfWeekInYearRange = chronology.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);
        assertNotNull(alignedDayOfWeekInYearRange);
    }
}
