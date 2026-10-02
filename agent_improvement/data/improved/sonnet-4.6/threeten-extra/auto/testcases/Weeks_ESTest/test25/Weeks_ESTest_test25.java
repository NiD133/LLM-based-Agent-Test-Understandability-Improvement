package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test25 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        // Weeks.ZERO represents 0 weeks; hashCode() returns the week count, so 0
        Weeks zeroWeeks = Weeks.ZERO;
        assertEquals(0, zeroWeeks.hashCode());
    }
}
