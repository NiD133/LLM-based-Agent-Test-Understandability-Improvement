package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test11 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Minutes.of(0) returns the ZERO singleton
        Minutes zeroMinutes = Minutes.of(0);
        // Subtracting 0 from ZERO should return the same ZERO instance (no-op optimization)
        Minutes afterSubtractingZero = Minutes.ZERO.minus(0);
        assertSame(afterSubtractingZero, zeroMinutes);
    }
}
