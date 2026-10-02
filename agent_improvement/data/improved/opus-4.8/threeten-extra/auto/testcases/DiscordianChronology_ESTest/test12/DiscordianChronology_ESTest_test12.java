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
public class DiscordianChronology_ESTest_test12 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the valid value range for the
     * ALIGNED_DAY_OF_WEEK_IN_YEAR field returns a non-null range.
     */
    @Test(timeout = 4000)
    public void rangeForAlignedDayOfWeekInYearReturnsNonNullRange() throws Throwable {
        DiscordianChronology discordianChronology = new DiscordianChronology();

        ValueRange alignedDayOfWeekInYearRange =
                discordianChronology.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);

        assertNotNull(alignedDayOfWeekInYearRange);
    }
}
