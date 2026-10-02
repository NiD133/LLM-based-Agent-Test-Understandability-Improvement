package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test04 extends JulianChronology_ESTest_scaffolding {

    /**
     * In the AD era, the proleptic year equals the year-of-era unchanged,
     * so {@code prolepticYear(AD, 652)} should return 652.
     */
    @Test(timeout = 4000)
    public void prolepticYearForAdEraEqualsYearOfEra() throws Throwable {
        JulianChronology julianChronology = new JulianChronology();
        int yearOfEra = 652;

        int prolepticYear = julianChronology.prolepticYear(JulianEra.AD, yearOfEra);

        assertEquals(652, prolepticYear);
    }
}
