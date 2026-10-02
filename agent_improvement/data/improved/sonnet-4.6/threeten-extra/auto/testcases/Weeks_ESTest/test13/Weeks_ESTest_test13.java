package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test13 extends Weeks_ESTest_scaffolding {

    // Adding a large negative value to Weeks.ONE (1 week) should yield 1 + (-565) = -564 weeks.
    @Test(timeout = 4000)
    public void test_plusNegativeWeeks_resultIsNegative() throws Throwable {
        Weeks oneWeek = Weeks.ONE;
        Weeks result = oneWeek.plus(-565);
        assertEquals(-564, result.getAmount());
    }
}
