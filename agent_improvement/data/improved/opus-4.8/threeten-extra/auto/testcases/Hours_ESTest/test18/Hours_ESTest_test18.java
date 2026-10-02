package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test18 extends Hours_ESTest_scaffolding {

    /**
     * A negative number of hours should report itself as negative
     * while preserving the exact amount it was created with.
     */
    @Test(timeout = 4000)
    public void negativeHours_isNegativeAndRetainsAmount() throws Throwable {
        Hours negativeHours = Hours.of(-1967);

        assertTrue("Hours.of(-1967) should be negative", negativeHours.isNegative());
        assertEquals("amount should match the value passed to Hours.of",
                -1967, negativeHours.getAmount());
    }
}
