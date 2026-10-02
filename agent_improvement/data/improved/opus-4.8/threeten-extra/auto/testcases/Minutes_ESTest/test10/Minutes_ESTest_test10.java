package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test10 extends Minutes_ESTest_scaffolding {

    /**
     * Multiplying an amount by a scalar of 1 is a no-op, so it returns the very
     * same instance rather than a new one. Here ZERO multiplied by 1 yields the
     * ZERO singleton unchanged.
     */
    @Test(timeout = 4000)
    public void multiplyingZeroByOneReturnsSameInstance() throws Throwable {
        Minutes zero = Minutes.ZERO;

        Minutes result = zero.multipliedBy(1);

        assertSame(zero, result);
    }
}
