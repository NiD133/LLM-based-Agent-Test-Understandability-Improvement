package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test19 extends Years_ESTest_scaffolding {

    // Years.of(0) should not be negative and should have an amount of 0
    @Test(timeout = 4000)
    public void test_zeroYears_isNotNegative_andAmountIsZero() throws Throwable {
        Years zeroYears = Years.of(0);
        assertFalse(zeroYears.isNegative());
        assertEquals(0, zeroYears.getAmount());
    }
}
