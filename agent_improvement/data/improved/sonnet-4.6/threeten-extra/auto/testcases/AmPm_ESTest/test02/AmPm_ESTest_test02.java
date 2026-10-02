package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test02 extends AmPm_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_PM_get_AMPM_OF_DAY_returns1() throws Throwable {
        AmPm pm = AmPm.PM;
        ChronoField amPmOfDayField = ChronoField.AMPM_OF_DAY;
        int amPmValue = pm.get(amPmOfDayField);
        assertEquals(1, amPmValue);
    }
}
