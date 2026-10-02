package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test07 extends Half_ESTest_scaffolding {

    /**
     * H1 (January to June) spans 181 days in a standard, non-leap year.
     */
    @Test(timeout = 4000)
    public void lengthOfFirstHalfInStandardYearIs181Days() throws Throwable {
        Half firstHalf = Half.H1;

        int daysInStandardYear = firstHalf.length(false);

        assertEquals(181, daysInStandardYear);
    }
}
