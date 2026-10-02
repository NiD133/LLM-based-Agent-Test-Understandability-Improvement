package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test09 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that abs() on Seconds.ZERO returns the same singleton instance.
     * Since ZERO is non-negative, abs() should return 'this' without creating a new object.
     */
    @Test(timeout = 4000)
    public void test_absOnZeroReturnsSameSingletonInstance() throws Throwable {
        Seconds zero = Seconds.ZERO;
        Seconds absResult = zero.abs();
        // abs() of zero should be the identical object (same reference), not just an equal one
        assertSame(zero, absResult);
    }
}
