package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test28 extends Days_ESTest_scaffolding {

    /**
     * Verifies that {@link Days#ofWeeks(int)} converts a (negative) number of
     * weeks into days by multiplying it by 7.
     *
     * For -25018 weeks the resulting amount is -25018 * 7 = -175126 days.
     */
    @Test(timeout = 4000)
    public void ofWeeks_convertsNegativeWeeksToDays() throws Throwable {
        int weeks = -25018;
        int expectedDays = weeks * 7;

        Days days = Days.ofWeeks(weeks);

        // getUnits() is exercised to confirm it is callable on the result.
        days.getUnits();
        assertEquals(expectedDays, days.getAmount());
    }
}
