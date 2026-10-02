package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test19 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that {@link Weeks#ZERO} represents a non-negative, zero-valued amount:
     * {@code isNegative()} must report false while {@code isZero()} reports true.
     */
    @Test(timeout = 4000)
    public void zeroWeeksIsZeroAndNotNegative() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;

        assertFalse("ZERO weeks must not be negative", zeroWeeks.isNegative());
        assertTrue("ZERO weeks must report as zero", zeroWeeks.isZero());
    }
}
