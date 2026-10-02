package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test19 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_fromQuarterReturnsSameInstance() throws Throwable {
        // Quarter.from() short-circuits and returns the same Quarter instance
        // when the argument is already a Quarter (no new object is created).
        Quarter q4 = Quarter.Q4;
        Quarter result = Quarter.from(q4);
        assertSame(q4, result);
    }
}
