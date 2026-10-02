package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test07 extends Months_ESTest_scaffolding {

    /**
     * Subtracting 2635 from a single month yields a negative amount,
     * and abs() converts that negative amount back to its positive magnitude.
     */
    @Test(timeout = 4000)
    public void minusProducesNegativeAndAbsRestoresPositive() throws Throwable {
        Months oneMonth = Months.ONE;

        Months afterSubtracting = oneMonth.minus(2635);
        assertEquals(-2634, afterSubtracting.getAmount());

        Months absoluteValue = afterSubtracting.abs();
        assertEquals(2634, absoluteValue.getAmount());
    }
}
