package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test19 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link BritishCutoverChronology#dateEpochDay(long)} creates a
     * date for an arbitrary epoch-day value (3697 days after 1970-01-01).
     */
    @Test(timeout = 4000)
    public void dateEpochDay_returnsNonNullDate() throws Throwable {
        long epochDay = 3697L;

        BritishCutoverDate date = BritishCutoverChronology.INSTANCE.dateEpochDay(epochDay);

        assertNotNull(date);
    }
}
