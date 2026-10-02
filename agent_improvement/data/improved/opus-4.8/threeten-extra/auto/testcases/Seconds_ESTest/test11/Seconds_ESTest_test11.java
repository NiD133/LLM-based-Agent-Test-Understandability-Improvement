package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertSame;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test11 extends Seconds_ESTest_scaffolding {

    /**
     * Multiplying a Seconds amount by a scalar of 1 is a no-op, so the method
     * returns the very same instance rather than constructing a new one.
     */
    @Test(timeout = 4000)
    public void multipliedByOne_returnsSameInstance() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;

        Seconds result = zeroSeconds.multipliedBy(1);

        assertSame(zeroSeconds, result);
    }
}
