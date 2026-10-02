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
public class DiscordianChronology_ESTest_test06 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that the Discordian chronology reports a valid range
     * for the DAY_OF_MONTH field.
     */
    @Test(timeout = 4000)
    public void rangeForDayOfMonthIsNotNull() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        ValueRange dayOfMonthRange = chronology.range(ChronoField.DAY_OF_MONTH);

        assertNotNull(dayOfMonthRange);
    }
}
