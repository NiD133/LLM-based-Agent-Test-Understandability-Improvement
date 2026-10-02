package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test16 extends Years_ESTest_scaffolding {

    /**
     * Verifies that a {@code Years} amount created with a value of zero
     * is reported as zero by {@link Years#isZero()}.
     */
    @Test(timeout = 4000)
    public void isZero_returnsTrue_forZeroYears() throws Throwable {
        Years zeroYears = Years.of(0);

        boolean isZero = zeroYears.isZero();

        assertTrue("Years.of(0) should be considered zero", isZero);
    }
}
