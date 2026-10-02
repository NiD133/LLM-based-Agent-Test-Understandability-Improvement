package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test00 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that range() returns a non-null ValueRange for a standard ChronoField
     * (MINUTE_OF_DAY) that is not overridden by the Discordian calendar, falling
     * through to the default delegation in ChronoField.range().
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        DiscordianChronology discordianChronology = new DiscordianChronology();
        ValueRange minuteOfDayRange = discordianChronology.INSTANCE.range(ChronoField.MINUTE_OF_DAY);
        assertNotNull(minuteOfDayRange);
    }
}
