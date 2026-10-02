package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test18 extends DiscordianChronology_ESTest_scaffolding {

    // ISO epoch day -719528 corresponds to January 1, ISO year 1 (Discordian YOLD 1167),
    // which falls within the single Discordian era YOLD.
    private static final long EPOCH_DAY_ISO_YEAR_1_JAN_1 = -719528L;

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        DiscordianDate dateAtIsoYear1 = chronology.dateEpochDay(EPOCH_DAY_ISO_YEAR_1_JAN_1);
        assertEquals(DiscordianEra.YOLD, dateAtIsoYear1.getEra());
    }
}
