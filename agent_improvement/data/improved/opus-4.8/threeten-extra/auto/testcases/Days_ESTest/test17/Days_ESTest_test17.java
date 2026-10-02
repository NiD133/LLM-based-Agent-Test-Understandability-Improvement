package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test17 extends Days_ESTest_scaffolding {

    /**
     * Verifies that a negative number of weeks is converted to the equivalent
     * negative number of days (7 days per week) and that such a non-zero
     * amount is correctly reported as not being zero.
     */
    @Test(timeout = 4000)
    public void ofWeeks_withNegativeWeeks_convertsToDaysAndIsNotZero() throws Throwable {
        int negativeWeeks = -3574;
        int expectedDays = negativeWeeks * 7; // -25018

        Days negativeWeekAmount = Days.ofWeeks(negativeWeeks);

        assertEquals(expectedDays, negativeWeekAmount.getAmount());
        assertFalse("A negative day amount must not be considered zero",
                negativeWeekAmount.isZero());
    }
}
