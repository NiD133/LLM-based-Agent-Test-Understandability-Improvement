package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Clock;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test17 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that obtaining "now" through the Symmetry454 chronology yields the
     * same date as obtaining it directly from Symmetry454Date, when both read from
     * the same clock. A minute-ticking clock keeps the two reads on the same day.
     */
    @Test(timeout = 4000)
    public void chronologyDateNowMatchesSymmetry454DateNow() throws Throwable {
        Clock clock = MockClock.tickMinutes(ZoneOffset.UTC);

        Symmetry454Date dateFromSymmetry454Date = Symmetry454Date.now(clock);
        Symmetry454Chronology chronology = dateFromSymmetry454Date.getChronology();
        Symmetry454Date dateFromChronology = chronology.dateNow(clock);

        assertTrue(dateFromChronology.equals(dateFromSymmetry454Date));
    }
}
