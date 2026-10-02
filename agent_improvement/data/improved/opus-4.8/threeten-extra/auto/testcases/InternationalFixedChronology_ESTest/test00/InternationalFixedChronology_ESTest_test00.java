package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test00 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * In the International Fixed calendar there is a single era (CE), so the
     * proleptic year is identical to the year-of-era. Converting year 2099 of
     * the CE era should therefore yield the proleptic year 2099 unchanged.
     */
    @Test(timeout = 4000)
    public void prolepticYearEqualsYearOfEraForCommonEra() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        InternationalFixedEra commonEra = InternationalFixedEra.CE;

        int prolepticYear = chronology.prolepticYear(commonEra, 2099);

        assertEquals(2099, prolepticYear);
    }
}
