package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.TemporalAccessor;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test27 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that converting a ZonedDateTime to a Discordian ChronoZonedDateTime
     * via zonedDateTime(TemporalAccessor) succeeds and returns a non-null result.
     */
    @Test(timeout = 4000)
    public void test27() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        ZonedDateTime now = MockZonedDateTime.now();
        ChronoZonedDateTime<DiscordianDate> discordianZonedDateTime =
                chronology.zonedDateTime((TemporalAccessor) now);
        assertNotNull(discordianZonedDateTime);
    }
}
