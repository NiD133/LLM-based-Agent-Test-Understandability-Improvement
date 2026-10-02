package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertSame;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test12 extends Months_ESTest_scaffolding {

    /**
     * Subtracting zero months from zero months should yield zero months.
     * Because the result is unchanged, {@code minus} is expected to return
     * the very same cached {@link Months#ZERO} instance rather than a new object.
     */
    @Test(timeout = 4000)
    public void subtractingZeroFromZeroReturnsSameZeroInstance() throws Throwable {
        Months zero = Months.ZERO;

        Months result = zero.minus((TemporalAmount) zero);

        assertSame(zero, result);
    }
}
