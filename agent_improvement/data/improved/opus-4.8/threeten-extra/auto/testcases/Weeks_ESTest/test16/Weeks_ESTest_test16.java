package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test16 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that the ZERO constant reports itself as zero via isZero().
     */
    @Test(timeout = 4000)
    public void isZero_returnsTrue_forZeroWeeks() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;

        boolean isZero = zeroWeeks.isZero();

        assertTrue("Weeks.ZERO should be reported as zero", isZero);
    }
}
