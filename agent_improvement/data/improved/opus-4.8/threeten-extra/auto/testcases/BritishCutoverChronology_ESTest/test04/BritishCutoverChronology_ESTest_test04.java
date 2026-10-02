package org.threeten.extra.chrono;

import static org.junit.Assert.assertNotNull;

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test04 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * The chronology should provide a valid value range for the
     * ALIGNED_WEEK_OF_MONTH field.
     */
    @Test(timeout = 4000)
    public void range_forAlignedWeekOfMonth_returnsNonNullRange() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        ValueRange alignedWeekOfMonthRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_MONTH);

        assertNotNull(alignedWeekOfMonthRange);
    }
}
