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
     * Verifies that DiscordianChronology can build a Discordian zoned date-time
     * from an ISO ZonedDateTime, returning a non-null ChronoZonedDateTime.
     */
    @Test(timeout = 4000)
    public void zonedDateTimeFromTemporalAccessorReturnsNonNullResult() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        ZonedDateTime isoZonedDateTime = MockZonedDateTime.now();

        ChronoZonedDateTime<DiscordianDate> discordianZonedDateTime =
                chronology.zonedDateTime((TemporalAccessor) isoZonedDateTime);

        assertNotNull(discordianZonedDateTime);
    }
}
