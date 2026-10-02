package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test16 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * An epoch day far enough in the past to fall outside the supported year range
     * (year 1 to 1,000,000) must be rejected with a DateTimeException.
     */
    @Test(timeout = 4000)
    public void dateEpochDayBeforeSupportedRangeThrowsException() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        long epochDayBeforeYearOne = -719528L;

        try {
            chronology.dateEpochDay(epochDayBeforeYearOne);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for YearOfEra (valid values 1 - 1000000): -1
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
