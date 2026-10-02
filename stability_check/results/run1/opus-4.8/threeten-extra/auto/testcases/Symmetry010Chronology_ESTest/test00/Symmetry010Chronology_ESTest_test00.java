package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.chrono.Era;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test00 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * dateYearDay must reject a day-of-year of 0, since valid values start at 1.
     * The rejection surfaces as a DateTimeException thrown while validating the
     * day-of-year against its ValueRange.
     */
    @Test(timeout = 4000)
    public void dateYearDay_withDayOfYearZero_throwsDateTimeException() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        Era commonEra = Symmetry454Date.now().getEra();

        int yearOfEra = 4;
        int invalidDayOfYear = 0;

        try {
            chronology.dateYearDay(commonEra, yearOfEra, invalidDayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for DayOfYear (valid values 1 - 364/371): 0
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
