package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test18 extends Half_ESTest_scaffolding {

    // December (month 12) falls in the second half of the year (July–December)
    @Test(timeout = 4000)
    public void test_ofMonth_december_returnsH2() throws Throwable {
        Half result = Half.ofMonth(12);
        assertEquals(Half.H2, result);
    }
}
