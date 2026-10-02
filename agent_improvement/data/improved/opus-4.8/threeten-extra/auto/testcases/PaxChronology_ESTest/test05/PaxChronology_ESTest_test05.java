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
public class PaxChronology_ESTest_test05 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that the Pax chronology returns a (non-null) supported value range
     * for the ALIGNED_WEEK_OF_MONTH field.
     */
    @Test(timeout = 4000)
    public void rangeForAlignedWeekOfMonthIsNotNull() throws Throwable {
        PaxDate someEpochDate = PaxDate.ofEpochDay(146096L);
        PaxChronology paxChronology = someEpochDate.getChronology();

        ValueRange alignedWeekOfMonthRange = paxChronology.range(ChronoField.ALIGNED_WEEK_OF_MONTH);

        assertNotNull(alignedWeekOfMonthRange);
    }
}
