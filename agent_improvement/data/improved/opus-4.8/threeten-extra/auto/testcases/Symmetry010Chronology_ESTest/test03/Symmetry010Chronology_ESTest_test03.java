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
public class Symmetry010Chronology_ESTest_test03 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that {@link Symmetry010Chronology#range(ChronoField)} returns a
     * (non-null) value range for the PROLEPTIC_MONTH field.
     */
    @Test(timeout = 4000)
    public void range_forProlepticMonth_returnsNonNullValueRange() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        ValueRange prolepticMonthRange = chronology.range(ChronoField.PROLEPTIC_MONTH);

        assertNotNull(prolepticMonthRange);
    }
}
