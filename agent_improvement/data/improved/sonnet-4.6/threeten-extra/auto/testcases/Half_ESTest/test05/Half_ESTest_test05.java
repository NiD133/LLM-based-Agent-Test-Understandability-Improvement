package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test05 extends Half_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_H2_firstMonth_returnsJuly() throws Throwable {
        Half secondHalf = Half.H2;
        Month firstMonthOfSecondHalf = secondHalf.firstMonth();
        assertEquals(Month.JULY, firstMonthOfSecondHalf);
    }
}
