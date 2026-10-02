package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test07 extends Half_ESTest_scaffolding {

    // H1 (January–June) spans 181 days in a standard (non-leap) year
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Half firstHalf = Half.H1;
        int daysInStandardYear = firstHalf.length(false);
        assertEquals(181, daysInStandardYear);
    }
}
