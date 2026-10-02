package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.YearMonth;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test03 extends Minutes_ESTest_scaffolding {

    /**
     * Subtracting a Minutes amount from a YearMonth must fail, because a
     * YearMonth has no minute-of-day component and therefore does not support
     * the MINUTES unit. The subtraction should raise an
     * UnsupportedTemporalTypeException thrown from java.time.YearMonth.
     */
    @Test(timeout = 4000)
    public void subtractMinutesFromYearMonthThrowsUnsupportedTemporalType() throws Throwable {
        Minutes minusEightHundredNinetyNineHours = Minutes.ofHours(-899);
        YearMonth currentYearMonth = MockYearMonth.now();

        try {
            minusEightHundredNinetyNineHours.subtractFrom(currentYearMonth);
            fail("Expected UnsupportedTemporalTypeException: YearMonth does not support the MINUTES unit");
        } catch (UnsupportedTemporalTypeException expected) {
            // YearMonth.minus rejects the unsupported MINUTES unit.
            verifyException("java.time.YearMonth", expected);
        }
    }
}
