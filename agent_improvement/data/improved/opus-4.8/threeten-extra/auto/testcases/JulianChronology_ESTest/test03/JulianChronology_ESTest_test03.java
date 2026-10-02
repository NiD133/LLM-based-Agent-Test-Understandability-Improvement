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
public class JulianChronology_ESTest_test03 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies that the Julian chronology provides a value range for the
     * PROLEPTIC_MONTH field.
     */
    @Test(timeout = 4000)
    public void range_forProlepticMonth_returnsNonNullRange() throws Throwable {
        JulianChronology chronology = JulianChronology.INSTANCE;

        ValueRange prolepticMonthRange = chronology.range(ChronoField.PROLEPTIC_MONTH);

        assertNotNull(prolepticMonthRange);
    }
}
