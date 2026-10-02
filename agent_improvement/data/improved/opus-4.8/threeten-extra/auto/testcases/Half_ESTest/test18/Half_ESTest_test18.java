package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test18 extends Half_ESTest_scaffolding {

    /**
     * December (month 12) falls in the second half of the year,
     * so {@link Half#ofMonth(int)} should return {@link Half#H2}.
     */
    @Test(timeout = 4000)
    public void ofMonth_december_returnsSecondHalf() throws Throwable {
        Half result = Half.ofMonth(12);
        assertEquals(Half.H2, result);
    }
}
