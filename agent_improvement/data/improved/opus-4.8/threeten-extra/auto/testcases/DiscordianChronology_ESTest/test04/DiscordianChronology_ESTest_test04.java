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
public class DiscordianChronology_ESTest_test04 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that the Discordian chronology returns a non-null valid-value range
     * for the MONTH_OF_YEAR field.
     */
    @Test(timeout = 4000)
    public void rangeForMonthOfYearIsNotNull() throws Throwable {
        DiscordianChronology discordianChronology = DiscordianDate.now().getChronology();

        ValueRange monthOfYearRange = discordianChronology.range(ChronoField.MONTH_OF_YEAR);

        assertNotNull(monthOfYearRange);
    }
}
