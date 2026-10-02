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

    @Test(timeout = 4000)
    public void test06_pmSupportsAmPmOfDayField() throws Throwable {
        AmPm pm = AmPm.PM;
        ChronoField amPmOfDay = ChronoField.AMPM_OF_DAY;
        boolean isSupported = pm.isSupported(amPmOfDay);
        assertTrue(isSupported);
    }
}
