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

    // Verifies that a date obtained from the chronology using the current system clock
    // belongs to the Common Era (CE), as expected for any present-day date.
    @Test(timeout = 4000)
    public void testDateNowWithClockReturnsCommonEraDate() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        Clock systemClock = MockClock.systemDefaultZone();
        Symmetry010Date today = chronology.dateNow(systemClock);
        assertEquals(IsoEra.CE, today.getEra());
    }
}
