package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test14 extends Days_ESTest_scaffolding {

    /**
     * Verifies that multiplying a negative Days amount by a negative scalar
     * yields a positive amount, while the original amount stays negative.
     */
    @Test(timeout = 4000)
    public void multiplyingTwoNegativesGivesPositiveDays() throws Throwable {
        // -3386 weeks = -3386 * 7 = -23702 days (negative amount).
        Days negativeDays = Days.ofWeeks(-3386);
        assertFalse("a negative number of weeks is not positive", negativeDays.isPositive());

        // -23702 days * -3386 = 80254972 days (negative times negative is positive).
        Days positiveDays = negativeDays.multipliedBy(-3386);

        assertEquals(80254972, positiveDays.getAmount());
        assertTrue("negative times negative scalar should be positive", positiveDays.isPositive());
    }
}
