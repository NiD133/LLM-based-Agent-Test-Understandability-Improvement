package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test07 extends PaxChronology_ESTest_scaffolding {

    /**
     * For the "Before Current Era" (BCE), proleptic years run backwards, so the
     * proleptic year is computed as {@code 1 - yearOfEra}. Therefore year-of-era
     * 99 in the BCE era maps to proleptic year 1 - 99 = -98.
     */
    @Test(timeout = 4000)
    public void prolepticYearForBceEraIsOneMinusYearOfEra() throws Throwable {
        PaxChronology paxChronology = PaxChronology.INSTANCE;
        PaxEra bceEra = PaxEra.BCE;
        int yearOfEra = 99;

        int prolepticYear = paxChronology.prolepticYear(bceEra, yearOfEra);

        assertEquals(-98, prolepticYear);
    }
}
