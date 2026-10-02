package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test16 extends PaxChronology_ESTest_scaffolding {

    /**
     * Proleptic year 0 is before the current era in the Pax calendar,
     * so any date within it must report PaxEra.BCE.
     */
    @Test(timeout = 4000)
    public void test_dateYearDay_prolepticYearZero_isInBceEra() throws Throwable {
        PaxChronology paxChronology = PaxChronology.INSTANCE;
        PaxDate date = paxChronology.dateYearDay(0, 14);
        assertEquals(PaxEra.BCE, date.getEra());
    }
}
