package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.YearMonth;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test10 extends DayOfMonth_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isValidYearMonth_whenYearMonthIsNull_returnsFalse() throws Throwable {
        // EvoSuite mocks the system clock so that DayOfMonth.now() returns day 14
        DayOfMonth dayOfMonth = DayOfMonth.now();
        assertEquals(14, dayOfMonth.getValue());

        boolean isValid = dayOfMonth.isValidYearMonth((YearMonth) null);

        assertFalse(isValid);
    }
}
