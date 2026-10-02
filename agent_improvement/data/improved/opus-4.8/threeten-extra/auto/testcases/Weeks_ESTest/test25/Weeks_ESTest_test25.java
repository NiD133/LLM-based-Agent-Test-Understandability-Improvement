package org.threeten.extra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test25 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that calling hashCode() on the zero-weeks constant executes
     * without throwing. Weeks.hashCode() simply returns the weeks value, so
     * Weeks.ZERO must hash without error.
     */
    @Test(timeout = 4000)
    public void hashCodeOfZeroWeeksDoesNotThrow() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;

        zeroWeeks.hashCode();
    }
}
