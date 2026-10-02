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
     * For an era before Christ (BC), the proleptic year counts backwards from year 1:
     * year-of-era 538 BC corresponds to proleptic year 1 - 538 = -537.
     */
    @Test(timeout = 4000)
    public void prolepticYearForBcEraNegatesYearOfEra() throws Throwable {
        BritishCutoverChronology chronology = BritishCutoverChronology.INSTANCE;

        int prolepticYear = chronology.prolepticYear(JulianEra.BC, 538);

        assertEquals(-537, prolepticYear);
    }
}
