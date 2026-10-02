package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test06 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void firstMonth_ofQ1_returnsJanuary() throws Throwable {
        Quarter quarter = Quarter.Q1;
        Month firstMonth = quarter.firstMonth();
        assertEquals(Month.JANUARY, firstMonth);
    }
}
