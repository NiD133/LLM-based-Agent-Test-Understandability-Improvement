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
public class BritishCutoverChronology_ESTest_test12 extends BritishCutoverChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_dateYearDay_throwsDateTimeException_whenDayOfYearIsNegative() throws Throwable {
        // A negative day-of-year is outside the valid range (1–365/366), so
        // dateYearDay must reject it with a DateTimeException from ValueRange validation.
        try {
            BritishCutoverChronology.INSTANCE.dateYearDay(-1002, -1002);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Expected message: "Invalid value for DayOfYear (valid values 1 - 365/366): -1002"
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
