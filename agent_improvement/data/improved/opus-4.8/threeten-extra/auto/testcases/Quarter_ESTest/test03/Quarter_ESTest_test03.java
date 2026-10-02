package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Month;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test03 extends Quarter_ESTest_scaffolding {

    /**
     * The third quarter (July to September) starts in July,
     * so {@link Quarter#firstMonth()} on Q3 should return {@link Month#JULY}.
     */
    @Test(timeout = 4000)
    public void firstMonthOfThirdQuarterIsJuly() throws Throwable {
        Month firstMonthOfQ3 = Quarter.Q3.firstMonth();

        assertEquals(Month.JULY, firstMonthOfQ3);
    }
}
