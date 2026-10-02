package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test08 extends Months_ESTest_scaffolding {

    /**
     * The absolute value of a positive amount (one month) is itself,
     * which remains positive.
     */
    @Test(timeout = 4000)
    public void absOfOneMonthIsPositive() throws Throwable {
        Months oneMonth = Months.ONE;

        Months absoluteValue = oneMonth.abs();

        assertTrue(absoluteValue.isPositive());
    }
}
