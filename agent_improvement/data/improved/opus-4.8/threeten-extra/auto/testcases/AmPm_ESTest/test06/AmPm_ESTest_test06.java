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
     * AmPm supports the AMPM_OF_DAY field, so isSupported should return true for it.
     */
    @Test(timeout = 4000)
    public void isSupported_returnsTrue_forAmPmOfDayField() throws Throwable {
        AmPm pm = AmPm.PM;

        boolean supported = pm.isSupported(ChronoField.AMPM_OF_DAY);

        assertTrue(supported);
    }
}
