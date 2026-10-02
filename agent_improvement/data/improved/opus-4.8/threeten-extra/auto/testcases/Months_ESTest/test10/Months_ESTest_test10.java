package org.threeten.extra;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test10 extends Months_ESTest_scaffolding {

    /**
     * Dividing one month by a large negative divisor truncates towards zero,
     * so 1 / -1271 yields a zero-month amount.
     */
    @Test(timeout = 4000)
    public void dividingOneMonthByLargeNegativeDivisorGivesZero() throws Throwable {
        Months oneMonth = Months.ONE;

        Months result = oneMonth.dividedBy(-1271);

        assertTrue(result.isZero());
    }
}
