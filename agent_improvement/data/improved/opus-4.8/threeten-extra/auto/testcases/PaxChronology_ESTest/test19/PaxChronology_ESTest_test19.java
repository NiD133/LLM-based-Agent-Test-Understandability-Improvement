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
public class PaxChronology_ESTest_test19 extends PaxChronology_ESTest_scaffolding {

    /**
     * A Pax date for the current instant should fall in the Common Era (CE),
     * since the system clock resolves to a present-day date.
     */
    @Test(timeout = 4000)
    public void dateNowFromClock_isInCommonEra() throws Throwable {
        Clock systemClock = MockClock.systemDefaultZone();

        PaxDate today = PaxChronology.INSTANCE.dateNow(systemClock);

        assertEquals(PaxEra.CE, today.getEra());
    }
}
