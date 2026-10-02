package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test16 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that the ZERO constant reports itself as zero via isZero().
     */
    @Test(timeout = 4000)
    public void isZeroReturnsTrueForZeroHours() throws Throwable {
        Hours zeroHours = Hours.ZERO;

        boolean isZero = zeroHours.isZero();

        assertTrue("Hours.ZERO should be reported as zero", isZero);
    }
}
