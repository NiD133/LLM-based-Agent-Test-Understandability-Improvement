package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test15 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_zerosIsNotPositiveAndIsZero() throws Throwable {
        Years zero = Years.ZERO;
        boolean isPositive = zero.isPositive();
        assertFalse(isPositive);
        assertTrue(zero.isZero());
    }
}
