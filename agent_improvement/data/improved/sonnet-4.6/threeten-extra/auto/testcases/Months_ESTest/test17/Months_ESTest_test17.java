package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test17 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void oneMonth_isNotNegative_andHasAmountOfOne() throws Throwable {
        Months oneMonth = Months.ONE;

        boolean isNegative = oneMonth.isNegative();

        assertFalse("Months.ONE should not be negative", isNegative);
        assertEquals("Months.ONE should have an amount of 1", 1, oneMonth.getAmount());
    }
}
