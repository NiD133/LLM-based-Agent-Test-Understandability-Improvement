package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test08 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that {@link Hours#abs()} returns the magnitude of a negative
     * amount, and that it does so without mutating the original instance
     * (Hours is immutable).
     */
    @Test(timeout = 4000)
    public void abs_onNegativeHours_returnsPositiveAndLeavesOriginalUnchanged() throws Throwable {
        Hours negativeHours = Hours.of(-3064);

        Hours absoluteHours = negativeHours.abs();

        assertEquals("abs() should drop the sign", 3064, absoluteHours.getAmount());
        assertEquals("original instance must remain unchanged", -3064, negativeHours.getAmount());
    }
}
