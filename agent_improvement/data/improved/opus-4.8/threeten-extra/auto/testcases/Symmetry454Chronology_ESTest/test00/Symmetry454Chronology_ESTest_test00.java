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
public class Symmetry454Chronology_ESTest_test00 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * The Symmetry454 chronology should provide a valid value range for a
     * supported temporal field. CLOCK_HOUR_OF_AMPM is a standard ISO field,
     * so range(...) must return a non-null ValueRange describing it.
     */
    @Test(timeout = 4000)
    public void range_forClockHourOfAmPmField_returnsValueRange() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();

        ValueRange clockHourRange = chronology.range(ChronoField.CLOCK_HOUR_OF_AMPM);

        assertNotNull(clockHourRange);
    }
}
