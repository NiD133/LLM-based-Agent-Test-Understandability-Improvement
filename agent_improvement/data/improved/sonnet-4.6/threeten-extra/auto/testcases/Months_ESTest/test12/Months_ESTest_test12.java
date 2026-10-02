package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test12 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // Subtracting zero months from zero should return the same ZERO singleton
        Months zero = Months.ZERO;
        Months result = zero.minus((TemporalAmount) zero);
        assertSame(result, zero);
    }
}
