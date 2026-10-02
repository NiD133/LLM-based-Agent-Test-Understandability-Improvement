package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test17 extends Half_ESTest_scaffolding {

    /**
     * June (month 6) falls in the first half of the year (January to June),
     * so {@link Half#ofMonth(int)} should return {@link Half#H1}.
     */
    @Test(timeout = 4000)
    public void ofMonth_forJune_returnsFirstHalf() throws Throwable {
        Half halfForJune = Half.ofMonth(6);

        assertEquals(Half.H1, halfForJune);
    }
}
