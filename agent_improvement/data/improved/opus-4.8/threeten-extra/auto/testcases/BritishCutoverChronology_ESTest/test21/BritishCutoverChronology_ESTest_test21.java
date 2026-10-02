package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test21 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that dateNow() returns a non-null BritishCutoverDate
     * representing the current date in the British cutover calendar system.
     */
    @Test(timeout = 4000)
    public void dateNow_returnsNonNullCurrentDate() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        BritishCutoverDate currentDate = chronology.dateNow();

        assertNotNull(currentDate);
    }
}
