package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test13 extends Months_ESTest_scaffolding {

    /**
     * Adding zero months to zero months yields zero months.
     */
    @Test(timeout = 4000)
    public void addingZeroToZeroStaysZero() throws Throwable {
        Months zero = Months.ZERO;

        Months result = zero.plus((TemporalAmount) zero);

        assertTrue(result.isZero());
    }
}
