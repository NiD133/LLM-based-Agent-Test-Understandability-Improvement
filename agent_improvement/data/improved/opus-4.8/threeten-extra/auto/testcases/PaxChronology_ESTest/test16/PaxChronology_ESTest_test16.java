package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test16 extends PaxChronology_ESTest_scaffolding {

    /**
     * A date built from proleptic-year 0 should fall in the BCE era,
     * because proleptic years of zero or below precede the common era.
     */
    @Test(timeout = 4000)
    public void dateInProlepticYearZero_isBeforeCommonEra() throws Throwable {
        PaxChronology paxChronology = new PaxChronology();
        int prolepticYear = 0;
        int dayOfYear = 14;

        PaxDate dateInYearZero = paxChronology.dateYearDay(prolepticYear, dayOfYear);

        assertEquals(PaxEra.BCE, dateInYearZero.getEra());
    }
}
