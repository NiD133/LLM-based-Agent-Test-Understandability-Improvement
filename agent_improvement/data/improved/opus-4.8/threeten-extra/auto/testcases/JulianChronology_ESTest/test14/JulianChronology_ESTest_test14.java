package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test14 extends JulianChronology_ESTest_scaffolding {

    /**
     * A date created from a positive proleptic-year and day-of-year should
     * fall in the 'Anno Domini' (AD) era.
     */
    @Test(timeout = 4000)
    public void dateYearDay_withPositiveYear_isInAdEra() throws Throwable {
        JulianChronology julianChronology = JulianChronology.INSTANCE;

        JulianDate julianDate = julianChronology.dateYearDay(2, 2);

        assertEquals(JulianEra.AD, julianDate.getEra());
    }
}
