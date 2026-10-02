package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test29 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that:
     *  - Minutes.ofHours converts hours to minutes (-8 hours == -480 minutes).
     *  - compareTo returns 0 when a Minutes instance is compared to itself.
     */
    @Test(timeout = 4000)
    public void comparingMinutesToItselfReturnsZero() throws Throwable {
        Minutes minusEightHours = Minutes.ofHours(-8);

        int comparisonResult = minusEightHours.compareTo(minusEightHours);

        assertEquals("-8 hours should equal -480 minutes", -480, minusEightHours.getAmount());
        assertEquals("a value compared to itself should be equal", 0, comparisonResult);
    }
}
