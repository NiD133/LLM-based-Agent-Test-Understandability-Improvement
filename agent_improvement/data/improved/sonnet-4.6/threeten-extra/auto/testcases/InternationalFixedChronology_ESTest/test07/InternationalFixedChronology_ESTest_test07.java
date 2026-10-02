package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test07 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that setting DAY_OF_YEAR to a value (1820) far beyond the valid range (1-365/366)
     * throws a DateTimeException reported by ValueRange.
     */
    @Test(timeout = 4000)
    public void test_withDayOfYear_outOfRange_throwsDateTimeException() throws Throwable {
        InternationalFixedDate today = InternationalFixedDate.now((ZoneId) ZoneOffset.UTC);
        long outOfRangeDayOfYear = 1820L;

        try {
            today.with((TemporalField) ChronoField.DAY_OF_YEAR, outOfRangeDayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
