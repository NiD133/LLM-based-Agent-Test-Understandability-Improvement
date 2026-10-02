package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test10 extends AmPm_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void ofHour_withMidnightHour_returnsAM() throws Throwable {
        AmPm result = AmPm.ofHour(0);
        assertEquals(AmPm.AM, result);
    }
}
