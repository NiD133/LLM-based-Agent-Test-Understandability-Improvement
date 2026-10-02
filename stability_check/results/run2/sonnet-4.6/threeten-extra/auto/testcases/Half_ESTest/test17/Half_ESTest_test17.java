package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test17 extends Half_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_ofMonth_june_returnsFirstHalf() throws Throwable {
        // June (month 6) falls within the first half of the year (January–June)
        Half result = Half.ofMonth(6);

        assertEquals(Half.H1, result);
    }
}
