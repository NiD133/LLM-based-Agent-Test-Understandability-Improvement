package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.Era;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test05 extends JulianChronology_ESTest_scaffolding {

    /**
     * For the BC era, the proleptic year runs backwards from the epoch, so
     * year-of-era {@code n} maps to proleptic year {@code 1 - n}.
     * Here year-of-era 19 in the BC era is expected to yield proleptic year -18.
     */
    @Test(timeout = 4000)
    public void prolepticYear_forBcEra_returnsOneMinusYearOfEra() throws Throwable {
        JulianChronology julianChronology = new JulianChronology();
        Era bcEra = JulianEra.BC;
        int yearOfEra = 19;

        int prolepticYear = julianChronology.prolepticYear(bcEra, yearOfEra);

        assertEquals(-18, prolepticYear);
    }
}
