package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test24 extends DiscordianChronology_ESTest_scaffolding {

    // The Discordian calendar has 5 months, each with exactly 73 days (5 × 73 = 365 days/year).
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        DiscordianDate today = chronology.dateNow();
        assertEquals(73, today.lengthOfMonth());
    }
}
