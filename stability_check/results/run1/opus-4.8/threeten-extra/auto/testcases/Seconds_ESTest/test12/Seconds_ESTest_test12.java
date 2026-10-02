package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test12 extends Seconds_ESTest_scaffolding {

    /**
     * Subtracting zero seconds from zero seconds should yield the exact same
     * ZERO singleton instance rather than a newly allocated object.
     */
    @Test(timeout = 4000)
    public void subtractingZeroFromZeroReturnsSameZeroInstance() throws Throwable {
        Seconds zero = Seconds.ZERO;

        Seconds result = zero.minus((TemporalAmount) zero);

        assertSame(zero, result);
    }
}
