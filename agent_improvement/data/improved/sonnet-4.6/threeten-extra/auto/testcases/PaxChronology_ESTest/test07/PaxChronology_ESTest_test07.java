package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Tests that prolepticYear converts a BCE year-of-era to the correct negative proleptic year.
 * For BCE era: prolepticYear = 1 - yearOfEra, so yearOfEra=99 yields -98.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test07 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        PaxEra bceEra = PaxEra.BCE;

        // Access prolepticYear via the singleton INSTANCE (same as original: chronology.INSTANCE)
        int prolepticYear = chronology.INSTANCE.prolepticYear(bceEra, 99);

        // BCE year 99 maps to proleptic year 1 - 99 = -98
        assertEquals(-98, prolepticYear);
    }
}
