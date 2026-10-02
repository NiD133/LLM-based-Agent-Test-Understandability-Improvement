package org.threeten.extra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test16 extends Months_ESTest_scaffolding {

    /**
     * Verifies that creating a {@link Months} from a negative number of years
     * converts the years to months (multiplying by 12) and is reported as negative.
     */
    @Test(timeout = 4000)
    public void ofYears_withNegativeYears_convertsToNegativeMonths() throws Throwable {
        int negativeYears = -2475;
        int expectedMonths = negativeYears * 12; // -29700

        Months months = Months.ofYears(negativeYears);

        assertTrue("A negative number of years should yield a negative amount", months.isNegative());
        assertEquals(expectedMonths, months.getAmount());
    }
}
