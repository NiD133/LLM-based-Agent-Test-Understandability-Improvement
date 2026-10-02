package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Clock;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test25 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that the current Symmetry010 date obtained from a present-day clock
     * falls in the Common Era (CE), since the clock resolves to a year well after CE 1.
     */
    @Test(timeout = 4000)
    public void dateNowFromCurrentClockHasCommonEra() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        Clock currentClock = MockClock.systemDefaultZone();

        Symmetry010Date today = chronology.dateNow(currentClock);

        assertEquals(IsoEra.CE, today.getEra());
    }
}
