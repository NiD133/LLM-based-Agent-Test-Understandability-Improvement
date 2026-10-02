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
public class DiscordianChronology_ESTest_test07 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the supported value range for the
     * ALIGNED_WEEK_OF_YEAR field returns a (non-null) range.
     */
    @Test(timeout = 4000)
    public void rangeForAlignedWeekOfYearReturnsRange() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        ValueRange alignedWeekOfYearRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_YEAR);

        assertNotNull(alignedWeekOfYearRange);
    }
}
