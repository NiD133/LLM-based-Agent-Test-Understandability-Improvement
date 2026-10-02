package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test19 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that the constant {@link Hours#ZERO} represents an amount of
     * zero hours: it is recognised as zero and is not considered negative.
     */
    @Test(timeout = 4000)
    public void zeroHoursIsZeroAndNotNegative() throws Throwable {
        Hours zeroHours = Hours.ZERO;

        assertFalse("Zero hours must not be negative", zeroHours.isNegative());
        assertTrue("Zero hours must report itself as zero", zeroHours.isZero());
    }
}
