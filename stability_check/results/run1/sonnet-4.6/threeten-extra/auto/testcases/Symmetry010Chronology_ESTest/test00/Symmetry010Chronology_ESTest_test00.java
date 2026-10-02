package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test00 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that {@code dateYearDay} throws a {@link DateTimeException} when
     * day-of-year is 0, which is below the valid range of 1–364 (or 1–371 in a leap year).
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        // Symmetry454Date shares ISO eras (CE/BCE); obtain the current era from it
        IsoEra currentEra = Symmetry454Date.now().getEra();

        // Day-of-year value 0 is invalid (valid range starts at 1), so a DateTimeException is expected
        try {
            chronology.dateYearDay((Era) currentEra, 4, 0);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Invalid value for DayOfYear (valid values 1 - 364/371): 0
            //
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
