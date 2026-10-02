package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test07 extends JulianChronology_ESTest_scaffolding {

    /**
     * A Julian date a few days after the epoch (1970-01-01) should fall in the
     * 'Anno Domini' (AD) era.
     */
    @Test(timeout = 4000)
    public void dateFromEpochDayIsInAdEra() throws Throwable {
        JulianChronology julianChronology = new JulianChronology();

        JulianDate dateTwoDaysAfterEpoch = julianChronology.INSTANCE.dateEpochDay(2L);

        assertEquals(JulianEra.AD, dateTwoDaysAfterEpoch.getEra());
    }
}
