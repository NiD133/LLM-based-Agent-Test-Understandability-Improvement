package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test18 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that a {@code Weeks} amount built from a negative number of weeks
     * reports itself as negative while still exposing the original amount.
     */
    @Test(timeout = 4000)
    public void negativeWeeks_isNegativeReturnsTrue() throws Throwable {
        Weeks negativeFourWeeks = Weeks.of(-4);

        boolean isNegative = negativeFourWeeks.isNegative();

        assertEquals(-4, negativeFourWeeks.getAmount());
        assertTrue("A -4 week amount should be reported as negative", isNegative);
    }
}
