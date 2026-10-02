package org.threeten.extra;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test23 extends DayOfYear_ESTest_scaffolding {

    /**
     * A {@link DayOfYear} carries only a day-of-year field, so it does not provide
     * enough information to build a {@link java.time.YearMonth}. Attempting the
     * conversion must therefore fail with a {@link DateTimeException} thrown from
     * {@code java.time.YearMonth}.
     */
    @Test(timeout = 4000)
    public void yearMonthFromDayOfYearThrowsDateTimeException() throws Throwable {
        DayOfYear dayOfYear = DayOfYear.now();

        try {
            MockYearMonth.from(dayOfYear);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException expected) {
            // Unable to obtain YearMonth from TemporalAccessor: a DayOfYear has no month information
            verifyException("java.time.YearMonth", expected);
        }
    }
}
