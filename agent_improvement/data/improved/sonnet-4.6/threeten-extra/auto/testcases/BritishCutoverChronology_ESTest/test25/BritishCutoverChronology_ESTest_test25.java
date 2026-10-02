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

    // getCutover() should return the Julian-to-Gregorian cutover date (14 Sep 1752)
    @Test(timeout = 4000)
    public void test25() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        LocalDate cutoverDate = chronology.getCutover();
        assertNotNull(cutoverDate);
    }
}
