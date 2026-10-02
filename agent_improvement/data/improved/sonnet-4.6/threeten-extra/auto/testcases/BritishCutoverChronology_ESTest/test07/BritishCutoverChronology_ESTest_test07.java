package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test07 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * For BC era, prolepticYear(BC, yearOfEra) returns 1 - yearOfEra.
     * BC year 538 maps to proleptic year 1 - 538 = -537.
     */
    @Test(timeout = 4000)
    public void prolepticYear_whenEraIsBcAndYearOfEraIs538_returnsNegative537() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        int prolepticYear = chronology.INSTANCE.prolepticYear(JulianEra.BC, 538);
        assertEquals(-537, prolepticYear);
    }
}
