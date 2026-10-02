package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test11 extends Hours_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Dividing Hours.ZERO by any non-zero divisor yields zero, which is the same ZERO singleton
        Hours zero = Hours.ZERO;
        Hours result = Hours.ZERO.dividedBy(-3);
        assertSame(result, zero);
    }
}
