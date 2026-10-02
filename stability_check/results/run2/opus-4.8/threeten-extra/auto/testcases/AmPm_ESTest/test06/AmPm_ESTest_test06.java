package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test06 extends AmPm_ESTest_scaffolding {

    /**
     * Verifies that AmPm reports the AMPM_OF_DAY field as supported,
     * since that is the only ChronoField an AmPm value can be queried for.
     */
    @Test(timeout = 4000)
    public void amPmSupportsAmPmOfDayField() throws Throwable {
        AmPm pm = AmPm.PM;

        boolean isAmPmOfDaySupported = pm.isSupported(ChronoField.AMPM_OF_DAY);

        assertTrue(isAmPmOfDaySupported);
    }
}
