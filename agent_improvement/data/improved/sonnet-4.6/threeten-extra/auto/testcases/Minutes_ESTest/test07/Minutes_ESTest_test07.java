package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test07 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_absOnZeroReturnsSameInstance() throws Throwable {
        // abs() on zero should return `this` rather than allocating a new object,
        // because zero is non-negative and the implementation returns `this` unchanged.
        Minutes zeroMinutes = Minutes.ZERO;
        Minutes absResult = zeroMinutes.abs();
        assertSame(zeroMinutes, absResult);
    }
}
