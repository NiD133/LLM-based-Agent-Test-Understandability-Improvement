package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import java.time.Month;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test05 extends Half_ESTest_scaffolding {

    /**
     * The second half of the year (H2) covers July to December,
     * so its first month must be July.
     */
    @Test(timeout = 4000)
    public void firstMonthOfSecondHalfIsJuly() throws Throwable {
        Half secondHalf = Half.H2;

        Month firstMonth = secondHalf.firstMonth();

        assertEquals(Month.JULY, firstMonth);
    }
}
