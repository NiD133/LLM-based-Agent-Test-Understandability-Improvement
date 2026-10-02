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

    // Epoch day -719528 corresponds to the start of ISO proleptic year 0 (i.e., year -1 in CE terms).
    // The International Fixed calendar only supports years 1–1,000,000 (CE only), so this epoch day
    // is outside the valid range and must trigger a DateTimeException.
    private static final long EPOCH_DAY_BEFORE_YEAR_ONE = -719528L;

    @Test(timeout = 4000)
    public void test_dateEpochDay_throwsDateTimeException_whenEpochDayMapsToYearBeforeOne() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        try {
            chronology.dateEpochDay(EPOCH_DAY_BEFORE_YEAR_ONE);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Expected: "Invalid value for YearOfEra (valid values 1 - 1000000): -1"
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
