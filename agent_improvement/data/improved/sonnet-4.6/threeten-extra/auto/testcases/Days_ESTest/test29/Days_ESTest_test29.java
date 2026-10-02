package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test29 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testZeroDaysToStringReturnsIsoPeriodFormat() throws Throwable {
        // Days.ZERO is the constant representing zero days; toString() must produce ISO-8601 "P0D"
        String result = Days.ZERO.toString();
        assertEquals("P0D", result);
    }
}
