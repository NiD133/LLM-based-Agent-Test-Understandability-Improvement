package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test10 extends Days_ESTest_scaffolding {

    /**
     * Dividing the one-day amount by a divisor of 1 leaves the amount unchanged at 1.
     */
    @Test(timeout = 4000)
    public void dividingOneDayByOne_keepsAmountOfOne() throws Throwable {
        Days oneDay = Days.ONE;

        Days result = oneDay.dividedBy(1);

        assertEquals(1, result.getAmount());
    }
}
