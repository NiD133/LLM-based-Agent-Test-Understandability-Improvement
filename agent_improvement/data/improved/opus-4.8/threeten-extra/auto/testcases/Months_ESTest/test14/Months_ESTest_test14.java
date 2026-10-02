package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test14 extends Months_ESTest_scaffolding {

    /**
     * Months.ONE represents a positive amount of one month, so isPositive() returns true.
     */
    @Test(timeout = 4000)
    public void oneMonthIsPositive() throws Throwable {
        Months oneMonth = Months.ONE;

        boolean positive = oneMonth.isPositive();

        assertEquals(1, oneMonth.getAmount());
        assertTrue(positive);
    }
}
