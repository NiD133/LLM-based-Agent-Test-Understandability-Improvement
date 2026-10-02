package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test07 extends Minutes_ESTest_scaffolding {

    /**
     * The absolute value of zero minutes is still zero, and {@link Minutes#abs()}
     * returns the cached {@link Minutes#ZERO} instance rather than a new object.
     */
    @Test(timeout = 4000)
    public void absOfZeroReturnsSameZeroInstance() throws Throwable {
        Minutes zero = Minutes.ZERO;

        Minutes result = zero.abs();

        assertSame(zero, result);
    }
}
