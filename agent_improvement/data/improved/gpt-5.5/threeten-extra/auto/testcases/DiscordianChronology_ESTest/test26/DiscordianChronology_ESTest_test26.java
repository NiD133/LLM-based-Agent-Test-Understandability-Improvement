package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test26 extends DiscordianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        Clock defaultZoneClock = MockClock.systemDefaultZone();

        DiscordianDate currentDiscordianDate = chronology.INSTANCE.dateNow(defaultZoneClock);

        assertEquals(DiscordianEra.YOLD, currentDiscordianDate.getEra());
    }
}
