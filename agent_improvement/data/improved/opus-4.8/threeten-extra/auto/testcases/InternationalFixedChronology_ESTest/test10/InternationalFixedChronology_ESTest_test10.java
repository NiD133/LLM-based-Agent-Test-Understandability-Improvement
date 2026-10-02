package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test10 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that the chronology returns a non-null value range for a
     * supported field (the aligned week-of-month).
     */
    @Test(timeout = 4000)
    public void rangeForAlignedWeekOfMonthReturnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        ValueRange alignedWeekOfMonthRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_MONTH);

        assertNotNull(alignedWeekOfMonthRange);
    }
}
