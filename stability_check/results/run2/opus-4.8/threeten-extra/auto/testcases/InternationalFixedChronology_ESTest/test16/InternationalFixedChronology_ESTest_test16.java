package org.threeten.extra.chrono;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.DateTimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test16 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * An epoch day that maps to a year before the supported range must be rejected.
     *
     * <p>Epoch day -719528 corresponds to a date whose year-of-era resolves to -1,
     * which is outside the valid range 1 - 1000000. Creating the date therefore
     * fails with a {@link DateTimeException} raised while validating the value range.
     */
    @Test(timeout = 4000)
    public void dateEpochDay_withYearBeforeSupportedRange_throwsDateTimeException() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        long epochDayWithInvalidYear = -719528L;

        try {
            chronology.dateEpochDay(epochDayWithInvalidYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for YearOfEra (valid values 1 - 1000000): -1
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
