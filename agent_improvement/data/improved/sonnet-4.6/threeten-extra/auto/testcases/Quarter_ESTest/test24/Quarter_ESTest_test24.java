package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test24 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void minus_zeroQuarters_returnsSameQuarter() throws Throwable {
        // Subtracting zero quarters from Q4 should be a no-op — the result must still be Q4.
        Quarter result = Quarter.Q4.minus(0L);
        assertEquals(Quarter.Q4, result);
    }
}
