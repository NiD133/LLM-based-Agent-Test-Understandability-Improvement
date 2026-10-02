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
public class Symmetry454Chronology_ESTest_test18 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * dateYearDay should reject a day-of-year that falls outside the valid
     * range (1 - 364/371). Here 4960 is far beyond the maximum, so a
     * DateTimeException is expected from the ValueRange check.
     */
    @Test(timeout = 4000)
    public void dateYearDay_withDayOfYearOutOfRange_throwsDateTimeException() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();
        Era bceEra = IsoEra.BCE;
        int yearOfEra = 4960;
        int invalidDayOfYear = 4960;

        try {
            chronology.dateYearDay(bceEra, yearOfEra, invalidDayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for DayOfYear (valid values 1 - 364/371): 4960
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
