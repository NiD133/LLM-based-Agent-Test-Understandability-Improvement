package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test10 extends AmPm_ESTest_scaffolding {

    /**
     * Verifies that {@link AmPm#ofHour(int)} maps an early hour (midnight, 0)
     * to the morning half-day, AM.
     */
    @Test(timeout = 4000)
    public void ofHour_withMidnight_returnsAm() throws Throwable {
        AmPm result = AmPm.ofHour(0);

        assertEquals(AmPm.AM, result);
    }
}
