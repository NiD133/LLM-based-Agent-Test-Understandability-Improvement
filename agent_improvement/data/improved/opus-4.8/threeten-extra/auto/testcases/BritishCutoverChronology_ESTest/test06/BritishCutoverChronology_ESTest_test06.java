package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test06 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that the British cutover chronology exposes a valid value range
     * for the DAY_OF_YEAR field rather than returning null.
     */
    @Test(timeout = 4000)
    public void range_forDayOfYear_returnsNonNullRange() throws Throwable {
        BritishCutoverChronology chronology = BritishCutoverChronology.INSTANCE;

        ValueRange dayOfYearRange = chronology.range(ChronoField.DAY_OF_YEAR);

        assertNotNull(dayOfYearRange);
    }
}
