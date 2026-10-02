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
public class BritishCutoverChronology_ESTest_test03 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that the chronology returns a (non-null) valid value range
     * for the PROLEPTIC_MONTH field.
     */
    @Test(timeout = 4000)
    public void rangeForProlepticMonthReturnsValueRange() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        ValueRange prolepticMonthRange = chronology.range(ChronoField.PROLEPTIC_MONTH);

        assertNotNull(prolepticMonthRange);
    }
}
