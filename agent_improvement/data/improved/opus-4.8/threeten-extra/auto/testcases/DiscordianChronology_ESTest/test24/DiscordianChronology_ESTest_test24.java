package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test24 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * The Discordian calendar divides the year into five 73-day months
     * (also called seasons), so any Discordian date reports a month length
     * of 73 days. Here we obtain today's Discordian date and confirm that.
     */
    @Test(timeout = 4000)
    public void dateNow_returnsDateWhoseMonthHas73Days() throws Throwable {
        DiscordianChronology discordianChronology = new DiscordianChronology();

        DiscordianDate today = discordianChronology.dateNow();

        assertEquals(73, today.lengthOfMonth());
    }
}
