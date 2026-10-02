package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.Month;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test04 extends Half_ESTest_scaffolding {

    /**
     * The first half of the year (H1) spans January to June,
     * so its first month should be January.
     */
    @Test(timeout = 4000)
    public void firstMonthOfFirstHalfIsJanuary() throws Throwable {
        Month firstMonth = Half.H1.firstMonth();

        assertEquals(Month.JANUARY, firstMonth);
    }
}
