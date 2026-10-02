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
public class PaxChronology_ESTest_test14 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that creating a Pax date with an out-of-range month-of-year is rejected.
     * <p>
     * The Pax calendar only allows months 1 through 13 (or 14 in a leap year), so the
     * negative month value below must trigger a {@link DateTimeException} raised while
     * validating the value against {@code ValueRange}.
     */
    @Test(timeout = 4000)
    public void date_withMonthOutsideValidRange_throwsDateTimeException() throws Throwable {
        PaxChronology paxChronology = new PaxChronology();
        int prolepticYear = -1093;
        int invalidMonth = -1093;
        int dayOfMonth = 0;

        try {
            paxChronology.date(prolepticYear, invalidMonth, dayOfMonth);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for MonthOfYear (valid values 1 - 13/14): -1093
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
