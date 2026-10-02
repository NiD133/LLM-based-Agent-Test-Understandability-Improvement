package org.threeten.extra;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test11 extends Years_ESTest_scaffolding {

    /**
     * Dividing the constant ONE year by a divisor of 1 leaves the amount
     * unchanged, so the result still represents one (non-zero) year.
     */
    @Test(timeout = 4000)
    public void dividingOneYearByOne_isNotZero() throws Throwable {
        Years oneYear = Years.ONE;

        Years result = oneYear.dividedBy(1);

        assertFalse(result.isZero());
    }
}
