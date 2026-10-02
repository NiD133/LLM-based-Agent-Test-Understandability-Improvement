package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test30 extends Days_ESTest_scaffolding {

    private static final int WEEKS = -25018;
    private static final int DAYS_PER_WEEK = 7;
    private static final int EXPECTED_DAYS = WEEKS * DAYS_PER_WEEK; // -175126

    /**
     * Verifies that {@link Days#ofWeeks(int)} converts a negative number of weeks
     * into the equivalent (negative) number of days, and that a negative amount
     * is correctly reported as non-positive.
     */
    @Test(timeout = 4000)
    public void negativeWeeksConvertToNegativeDays() throws Throwable {
        Days negativeWeeks = Days.ofWeeks(WEEKS);

        // Converting the zero constant to a Period must not affect the result.
        Days.ZERO.toPeriod();

        assertEquals(EXPECTED_DAYS, negativeWeeks.getAmount());
        assertFalse(negativeWeeks.isPositive());
    }
}
