package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test13 extends AmPm_ESTest_scaffolding {

    /**
     * AmPm.of(int) follows the Calendar convention where 0 maps to AM and 1 maps to PM.
     * Verifies that passing the value 1 returns the PM constant.
     */
    @Test(timeout = 4000)
    public void of_withValueOne_returnsPm() throws Throwable {
        AmPm result = AmPm.of(1);

        assertEquals(AmPm.PM, result);
    }
}
