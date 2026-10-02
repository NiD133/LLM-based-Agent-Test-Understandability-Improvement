package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test25 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that the British cutover chronology exposes the date on which the
     * Julian-to-Gregorian cutover took effect.
     */
    @Test(timeout = 4000)
    public void getCutoverReturnsNonNullCutoverDate() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        LocalDate cutoverDate = chronology.getCutover();

        assertNotNull(cutoverDate);
    }
}
