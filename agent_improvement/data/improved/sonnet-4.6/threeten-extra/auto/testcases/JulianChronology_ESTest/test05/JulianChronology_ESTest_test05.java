package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test05 extends JulianChronology_ESTest_scaffolding {

    /**
     * BC year 19 converts to proleptic year -18 because prolepticYear(BC, y) = 1 - y.
     */
    @Test(timeout = 4000)
    public void test05_prolepticYearForBCEraIsOneMinusYearOfEra() throws Throwable {
        JulianChronology chronology = new JulianChronology();
        int prolepticYear = chronology.prolepticYear(JulianEra.BC, 19);
        assertEquals(-18, prolepticYear);
    }
}
