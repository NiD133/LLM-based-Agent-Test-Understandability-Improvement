package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test08 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that prolepticYear returns the year-of-era unchanged when the era is AD,
     * because proleptic year == year-of-era for the current era.
     */
    @Test(timeout = 4000)
    public void test08_prolepticYear_forAdEra_returnsYearOfEraUnchanged() throws Throwable {
        int yearOfEra = 538;
        int prolepticYear = BritishCutoverChronology.INSTANCE.prolepticYear(JulianEra.AD, yearOfEra);
        assertEquals(yearOfEra, prolepticYear);
    }
}
