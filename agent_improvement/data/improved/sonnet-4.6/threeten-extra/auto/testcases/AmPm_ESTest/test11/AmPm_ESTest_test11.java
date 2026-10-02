package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test11 extends AmPm_ESTest_scaffolding {

    // Hour 12 is the first hour of PM (noon), so ofHour(12) must return PM.
    @Test(timeout = 4000)
    public void test_ofHour_12_returnsPM() throws Throwable {
        AmPm result = AmPm.ofHour(12);
        assertEquals(AmPm.PM, result);
    }
}
