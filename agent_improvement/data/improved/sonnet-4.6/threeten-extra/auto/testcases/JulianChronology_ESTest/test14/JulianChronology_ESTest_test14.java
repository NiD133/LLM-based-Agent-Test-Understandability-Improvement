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
     * Verifies that dateYearDay with a positive proleptic year (year 2 AD, day 2)
     * produces a JulianDate whose era is AD.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // Proleptic year 2 maps to AD 2; day-of-year 2 is January 2nd
        JulianDate date = JulianChronology.INSTANCE.dateYearDay(2, 2);
        assertEquals(JulianEra.AD, date.getEra());
    }
}
