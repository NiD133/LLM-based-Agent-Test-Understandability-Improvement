package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test21 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testFromJulyMonthReturnsQ3() throws Throwable {
        Month july = Month.JULY;
        Quarter result = Quarter.from(july);
        assertEquals(Quarter.Q3, result);
    }
}
