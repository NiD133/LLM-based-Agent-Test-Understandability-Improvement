package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test06 extends Half_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testH1LengthInLeapYearIs182Days() throws Throwable {
        // H1 (January–June) spans 182 days in a leap year because February has 29 days
        int leapYearLength = Half.H1.length(true);
        assertEquals(182, leapYearLength);
    }
}
