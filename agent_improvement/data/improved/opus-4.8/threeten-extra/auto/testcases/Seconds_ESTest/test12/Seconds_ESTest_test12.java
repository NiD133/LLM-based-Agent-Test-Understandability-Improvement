package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertSame;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test12 extends Seconds_ESTest_scaffolding {

    /**
     * Subtracting zero seconds from zero seconds yields zero seconds.
     * Because the result is still zero, {@code minus} returns the shared
     * {@link Seconds#ZERO} singleton, so the result is the very same instance.
     */
    @Test(timeout = 4000)
    public void subtractingZeroFromZeroReturnsZeroSingleton() throws Throwable {
        Seconds zero = Seconds.ZERO;

        Seconds result = zero.minus((TemporalAmount) zero);

        assertSame(zero, result);
    }
}
