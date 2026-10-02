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

    // Valid day-of-year range for Symmetry010 is 1..364 (or 1..371 in a leap year); 0 is out of range
    private static final int INVALID_DAY_OF_YEAR = 0;
    private static final int YEAR_OF_ERA = 4;

    /**
     * Verifies that {@link Symmetry010Chronology#dateYearDay(Era, int, int)} throws
     * {@link DateTimeException} when the day-of-year argument is 0, which lies below
     * the valid minimum of 1.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        // Obtain the current era from today's Symmetry454 date to use as the era argument
        Symmetry454Date today = Symmetry454Date.now();
        IsoEra era = today.getEra();

        try {
            chronology.dateYearDay((Era) era, YEAR_OF_ERA, INVALID_DAY_OF_YEAR);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // ValueRange.checkValidValue rejects 0 because day-of-year must be in [1, 364/371]
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
