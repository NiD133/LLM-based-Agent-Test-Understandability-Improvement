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
public class BritishCutoverChronology_ESTest_test23 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Creating a date with an out-of-range month must fail: the month -992 is far
     * outside the valid 1-12 range, so date(year, month, day) is expected to throw
     * a DateTimeException raised while validating the month against its ValueRange.
     */
    @Test(timeout = 4000)
    public void dateWithMonthOutOfRangeThrowsDateTimeException() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        int invalidMonthOfYear = -992;

        try {
            chronology.date(-992, invalidMonthOfYear, -992);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for MonthOfYear (valid values 1 - 12): -992
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
