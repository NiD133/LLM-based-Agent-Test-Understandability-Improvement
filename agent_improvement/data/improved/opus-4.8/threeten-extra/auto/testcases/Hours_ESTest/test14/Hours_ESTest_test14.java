package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test14 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that a positive amount of hours reports its amount correctly
     * and is recognised as positive by {@link Hours#isPositive()}.
     */
    @Test(timeout = 4000)
    public void isPositive_returnsTrue_forPositiveHours() throws Throwable {
        Hours twoHours = Hours.of(2);

        boolean positive = twoHours.isPositive();

        assertEquals("getAmount should return the value supplied to Hours.of",
                2, twoHours.getAmount());
        assertTrue("two hours should be considered positive", positive);
    }
}
