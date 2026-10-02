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
     * Verifies that multiplying a negative {@code Days} amount by a negative
     * scalar yields a distinct, positive-valued instance, and that the two
     * instances compare as unequal in both directions.
     */
    @Test(timeout = 4000)
    public void multiplyingNegativeDaysByNegativeScalarProducesUnequalPositiveAmount() throws Throwable {
        // -3386 weeks == -3386 * 7 == -23702 days
        Days negativeDays = Days.ofWeeks(-3386);

        // -23702 days * -3386 == 80254972 days
        Days multipliedDays = negativeDays.multipliedBy(-3386);

        // The product has a different amount, so the instances are not equal (both directions).
        assertFalse(negativeDays.equals(multipliedDays));
        assertFalse(multipliedDays.equals((Object) negativeDays));

        // The original amount is negative, hence not positive.
        assertFalse(negativeDays.isPositive());

        // The product is the expected positive amount.
        assertEquals(80254972, multipliedDays.getAmount());
    }
}
