package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test11 extends AmPm_ESTest_scaffolding {

    /**
     * Noon (hour 12) is the first hour of the afternoon, so {@link AmPm#ofHour(int)}
     * should classify it as PM.
     */
    @Test(timeout = 4000)
    public void ofHour_withNoon_returnsPm() throws Throwable {
        AmPm result = AmPm.ofHour(12);

        assertEquals(AmPm.PM, result);
    }
}
