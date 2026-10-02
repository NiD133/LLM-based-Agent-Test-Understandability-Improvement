package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test14 extends AmPm_ESTest_scaffolding {

    /**
     * AmPm only supports the AMPM_OF_DAY field. Any other ChronoField,
     * such as MICRO_OF_SECOND, must be reported as unsupported.
     */
    @Test(timeout = 4000)
    public void isSupported_returnsFalse_forUnrelatedChronoField() throws Throwable {
        AmPm am = AmPm.of(0);
        assertEquals(AmPm.AM, am);

        boolean microOfSecondSupported = am.isSupported(ChronoField.MICRO_OF_SECOND);

        assertFalse(microOfSecondSupported);
    }
}
