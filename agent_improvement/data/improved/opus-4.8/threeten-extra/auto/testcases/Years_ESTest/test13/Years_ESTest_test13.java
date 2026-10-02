package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test13 extends Years_ESTest_scaffolding {

    /**
     * Dividing ONE year by 498 truncates to zero years (integer division),
     * and subtracting that zero amount from ONE leaves ONE unchanged.
     */
    @Test(timeout = 4000)
    public void dividingOneByLargeDivisorTruncatesToZeroAndSubtractingLeavesOriginal() throws Throwable {
        Years oneYear = Years.ONE;

        Years zeroYears = oneYear.dividedBy(498);
        assertEquals(0, zeroYears.getAmount());

        Years result = Years.ONE.minus((TemporalAmount) zeroYears);
        assertEquals(1, result.getAmount());
    }
}
