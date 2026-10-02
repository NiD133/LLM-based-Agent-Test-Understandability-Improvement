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
public class BritishCutoverChronology_ESTest_test01 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that the chronology returns a valid value range for the YEAR field.
     */
    @Test(timeout = 4000)
    public void range_forYearField_returnsValueRange() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        ValueRange yearRange = chronology.range(ChronoField.YEAR);

        assertNotNull(yearRange);
    }
}
