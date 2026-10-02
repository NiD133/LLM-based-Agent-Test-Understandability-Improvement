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
public class Symmetry454Chronology_ESTest_test03 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that the Symmetry454 chronology provides a value range for the
     * PROLEPTIC_MONTH field (rather than returning null or throwing).
     */
    @Test(timeout = 4000)
    public void range_forProlepticMonthField_returnsValueRange() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();

        ValueRange prolepticMonthRange = chronology.range(ChronoField.PROLEPTIC_MONTH);

        assertNotNull(prolepticMonthRange);
    }
}
