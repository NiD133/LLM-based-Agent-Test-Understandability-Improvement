package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test30 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that {@link Minutes#abs()} returns the magnitude of a negative
     * amount while leaving the original instance unchanged.
     */
    @Test(timeout = 4000)
    public void absOfNegativeHoursReturnsPositiveMinutes() throws Throwable {
        // -8 hours == -480 minutes
        Minutes negativeEightHours = Minutes.ofHours(-8);

        Minutes absoluteValue = negativeEightHours.abs();

        assertEquals("abs() should yield the positive magnitude in minutes",
                480, absoluteValue.getAmount());
        assertEquals("the original instance must remain unchanged",
                -480, negativeEightHours.getAmount());
    }
}
