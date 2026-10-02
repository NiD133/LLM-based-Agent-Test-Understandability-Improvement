package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test01 extends Days_ESTest_scaffolding {

    /**
     * Verifies that scaling a negative {@code Days} amount by a negative scalar
     * yields a positive amount, and that the original and scaled values are not equal.
     */
    @Test(timeout = 4000)
    public void multiplyingNegativeDaysByNegativeScalarProducesUnequalPositiveAmount() throws Throwable {
        // -3386 weeks == -3386 * 7 == -23702 days
        Days negativeDays = Days.ofWeeks(-3386);

        // (-23702 days) * (-3386) == 80254972 days
        Days scaledDays = negativeDays.multipliedBy(-3386);

        // The two amounts differ, so equals is false in both directions.
        assertFalse(negativeDays.equals(scaledDays));
        assertFalse(scaledDays.equals((Object) negativeDays));

        // The original amount is negative, hence not positive.
        assertFalse(negativeDays.isPositive());

        // The scaled amount holds the expected positive day count.
        assertEquals(80254972, scaledDays.getAmount());
    }
}
