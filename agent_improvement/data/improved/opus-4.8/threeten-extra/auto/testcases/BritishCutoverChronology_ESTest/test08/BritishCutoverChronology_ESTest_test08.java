package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test08 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * For the AD era, the proleptic year equals the year-of-era unchanged,
     * so converting year 538 in the AD era yields proleptic year 538.
     */
    @Test(timeout = 4000)
    public void prolepticYear_forAdEra_returnsYearOfEraUnchanged() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        int prolepticYear = chronology.prolepticYear(JulianEra.AD, 538);

        assertEquals(538, prolepticYear);
    }
}
