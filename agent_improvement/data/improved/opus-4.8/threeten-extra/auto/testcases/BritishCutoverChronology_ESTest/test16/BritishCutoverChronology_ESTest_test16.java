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
public class BritishCutoverChronology_ESTest_test16 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that dateNow(Clock) returns a non-null BritishCutoverDate
     * when given a clock for the system default time-zone.
     */
    @Test(timeout = 4000)
    public void dateNowWithClock_returnsNonNullDate() throws Throwable {
        BritishCutoverChronology chronology = BritishCutoverChronology.INSTANCE;
        Clock systemDefaultClock = MockClock.systemDefaultZone();

        BritishCutoverDate today = chronology.dateNow(systemDefaultClock);

        assertNotNull(today);
    }
}
