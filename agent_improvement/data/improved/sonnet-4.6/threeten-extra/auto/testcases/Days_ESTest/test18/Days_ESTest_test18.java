package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test18 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // -3574 weeks * 7 days/week = -25018 days
        Days negativeDays = Days.ofWeeks(-3574);

        boolean isNegative = negativeDays.isNegative();

        assertEquals(-25018, negativeDays.getAmount());
        assertTrue(isNegative);
    }
}
