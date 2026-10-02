package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Clock;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test26 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that the date produced by {@link DiscordianChronology#dateNow(Clock)}
     * reports YOLD as its era, since the Discordian calendar defines exactly one era.
     */
    @Test(timeout = 4000)
    public void dateNowFromClock_hasYoldEra() throws Throwable {
        DiscordianChronology discordianChronology = DiscordianChronology.INSTANCE;
        Clock systemClock = MockClock.systemDefaultZone();

        DiscordianDate currentDate = discordianChronology.dateNow(systemClock);

        assertEquals(DiscordianEra.YOLD, currentDate.getEra());
    }
}
