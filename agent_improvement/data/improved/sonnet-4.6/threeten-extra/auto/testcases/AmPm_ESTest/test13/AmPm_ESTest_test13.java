package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test13 extends AmPm_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void ofIntValue1_returnsPM() throws Throwable {
        AmPm result = AmPm.of(1);
        assertEquals(AmPm.PM, result);
    }
}
