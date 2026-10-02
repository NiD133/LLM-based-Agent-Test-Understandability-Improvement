package org.threeten.extra.chrono;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test00 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that prolepticYear returns the year-of-era unchanged for the CE era,
     * since the International Fixed calendar has only one era and year numbering starts at 1.
     */
    @Test(timeout = 4000)
    public void test_prolepticYear_ceEra_returnsYearOfEraUnchanged() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        int prolepticYear = chronology.prolepticYear(InternationalFixedEra.CE, 2099);
        assertEquals(2099, prolepticYear);
    }
}
