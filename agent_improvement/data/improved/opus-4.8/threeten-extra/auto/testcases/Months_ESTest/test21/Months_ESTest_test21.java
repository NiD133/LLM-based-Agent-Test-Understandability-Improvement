package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test21 extends Months_ESTest_scaffolding {

    /**
     * Verifies that a {@code Months} amount created from zero years
     * is reported as zero.
     */
    @Test(timeout = 4000)
    public void zeroYearsProducesZeroMonths() throws Throwable {
        Months zeroMonths = Months.ofYears(0);

        assertTrue("Months.ofYears(0) should be zero", zeroMonths.isZero());
    }
}
