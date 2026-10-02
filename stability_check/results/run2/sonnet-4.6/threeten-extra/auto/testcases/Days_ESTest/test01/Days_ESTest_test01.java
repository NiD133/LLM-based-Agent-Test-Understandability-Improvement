package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test01 extends Days_ESTest_scaffolding {

    /**
     * Verifies that multiplying a negative Days amount by a large negative scalar
     * produces a distinct positive result, while the original remains non-positive.
     *
     * -3386 weeks * 7 = -23,702 days (original)
     * -23,702 days * -3386 = 80,254,972 days (multiplied)
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // -3386 weeks = -3386 * 7 = -23,702 days
        Days negativeWeeksDays = Days.ofWeeks(-3386);

        // multiplying a negative by -3386 produces a large positive: -23702 * -3386 = 80,254,972
        Days largePositiveDays = negativeWeeksDays.multipliedBy(-3386);

        // the original and the product represent different day counts
        assertFalse(negativeWeeksDays.equals(largePositiveDays));
        assertFalse(largePositiveDays.equals((Object) negativeWeeksDays));

        // the original is derived from a negative number of weeks, so it is not positive
        assertFalse(negativeWeeksDays.isPositive());

        // confirm the exact product: -23702 * -3386 = 80,254,972
        assertEquals(80254972, largePositiveDays.getAmount());
    }
}
