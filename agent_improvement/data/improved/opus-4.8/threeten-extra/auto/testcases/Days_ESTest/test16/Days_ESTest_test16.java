package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test16 extends Days_ESTest_scaffolding {

    /**
     * Verifies that the {@link Days#ZERO} constant is recognised as a
     * zero-length amount by {@link Days#isZero()}.
     */
    @Test(timeout = 4000)
    public void isZero_returnsTrue_forZeroConstant() throws Throwable {
        Days zeroDays = Days.ZERO;

        boolean isZero = zeroDays.isZero();

        assertTrue("Days.ZERO should be reported as zero", isZero);
    }
}
