package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test17 extends JulianChronology_ESTest_scaffolding {

    /**
     * The current Julian date, obtained from the system clock, should fall in the
     * 'Anno Domini' (AD) era.
     */
    @Test(timeout = 4000)
    public void dateNow_returnsDateInAdEra() throws Throwable {
        JulianDate today = JulianChronology.INSTANCE.dateNow();

        assertEquals(JulianEra.AD, today.getEra());
    }
}
