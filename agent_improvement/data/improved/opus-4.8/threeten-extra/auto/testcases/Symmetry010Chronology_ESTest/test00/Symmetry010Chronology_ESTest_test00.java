package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
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
     * {@code dateYearDay} should reject a day-of-year of 0, since the valid
     * range for the Symmetry010 calendar is 1 to 364 (or 371 in a leap year).
     * The validation is performed by {@link java.time.temporal.ValueRange},
     * which throws a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void dateYearDay_withDayOfYearZero_throwsDateTimeException() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        Era era = Symmetry454Date.now().getEra();

        int yearOfEra = 4;
        int invalidDayOfYear = 0;

        try {
            chronology.dateYearDay(era, yearOfEra, invalidDayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for DayOfYear (valid values 1 - 364/371): 0
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
