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
public class DiscordianChronology_ESTest_test01 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that the Discordian chronology returns a (non-null) valid value
     * range for the YEAR field.
     */
    @Test(timeout = 4000)
    public void rangeForYearFieldReturnsNonNullRange() throws Throwable {
        DiscordianChronology chronology = DiscordianDate.now().getChronology();

        ValueRange yearRange = chronology.range(ChronoField.YEAR);

        assertNotNull(yearRange);
    }
}
