package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test24 extends Days_ESTest_scaffolding {

    // Days.ofWeeks(0) converts 0 weeks to 0 days, so isZero() must return true
    @Test(timeout = 4000)
    public void test_ofWeeks_zeroWeeks_isZero() throws Throwable {
        Days zeroDays = Days.ofWeeks(0);
        assertTrue(zeroDays.isZero());
    }
}
