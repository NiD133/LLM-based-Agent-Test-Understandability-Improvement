package org.threeten.extra.chrono;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

import static org.junit.Assert.assertEquals;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test29 extends DiscordianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test29_getCalendarType_returnsDiscordian() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        String calendarType = chronology.getCalendarType();
        assertEquals("discordian", calendarType);
    }
}
