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
     * Verifies that {@link DiscordianChronology#range(ChronoField)} returns a non-null
     * {@link ValueRange} for the {@code ALIGNED_DAY_OF_WEEK_IN_YEAR} field, confirming
     * that the Discordian chronology provides a valid range for this field.
     */
    @Test(timeout = 4000)
    public void testRangeForAlignedDayOfWeekInYear() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        ValueRange alignedDayOfWeekInYearRange = chronology.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);

        assertNotNull(alignedDayOfWeekInYearRange);
    }
}
