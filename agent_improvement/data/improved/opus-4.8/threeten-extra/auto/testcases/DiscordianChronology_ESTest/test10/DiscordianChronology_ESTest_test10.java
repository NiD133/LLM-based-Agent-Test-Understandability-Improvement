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
public class DiscordianChronology_ESTest_test10 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that the Discordian chronology exposes a valid value range for the
     * ALIGNED_DAY_OF_WEEK_IN_MONTH field.
     */
    @Test(timeout = 4000)
    public void range_forAlignedDayOfWeekInMonth_returnsNonNullRange() throws Throwable {
        DiscordianChronology chronology = DiscordianDate.now().getChronology();

        ValueRange alignedDayOfWeekInMonthRange =
                chronology.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);

        assertNotNull(alignedDayOfWeekInMonthRange);
    }
}
