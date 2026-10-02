package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test14 extends Years_ESTest_scaffolding {

    /**
     * Adding zero years to zero years should yield a zero-valued amount.
     */
    @Test(timeout = 4000)
    public void plusZeroYearsToZeroRemainsZero() throws Throwable {
        Years zeroYears = Years.ZERO;

        Years sum = zeroYears.plus((TemporalAmount) zeroYears);

        assertTrue(sum.isZero());
    }
}
