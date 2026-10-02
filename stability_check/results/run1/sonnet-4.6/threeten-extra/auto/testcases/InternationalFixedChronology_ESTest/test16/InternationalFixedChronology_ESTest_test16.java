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
     * The IFC calendar only supports years 1 through 1,000,000.
     * Epoch day -719528 maps to IFC year 0, which is before the valid range.
     * Calling dateEpochDay with this value must throw DateTimeException
     * with a message indicating the year-of-era (-1) is out of range.
     */
    @Test(timeout = 4000)
    public void test_dateEpochDay_yearBeforeEpoch_throwsDateTimeException() throws Throwable {
        // Epoch day -719528 corresponds to the day immediately before IFC year 1
        // (i.e., IFC year 0), which is outside the supported year range [1, 1000000].
        final long epochDayBeforeIfcEra = -719528L;

        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;

        try {
            chronology.dateEpochDay(epochDayBeforeIfcEra);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Expected: "Invalid value for YearOfEra (valid values 1 - 1000000): -1"
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
