package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test18 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * A Discordian date created from an epoch day should report the only
     * Discordian era, YOLD ("Year of Our Lady of Discord").
     */
    @Test(timeout = 4000)
    public void dateFromEpochDay_hasYoldEra() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        // Epoch day -719528 corresponds to ISO 0001-01-01, a valid Discordian date.
        long epochDay = -719528L;
        DiscordianDate date = chronology.dateEpochDay(epochDay);

        assertEquals(DiscordianEra.YOLD, date.getEra());
    }
}
