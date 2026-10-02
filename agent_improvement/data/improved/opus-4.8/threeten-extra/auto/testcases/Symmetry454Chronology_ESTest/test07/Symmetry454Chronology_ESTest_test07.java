package org.threeten.extra.chrono;

import static org.junit.Assert.assertNotNull;

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test07 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * The chronology should provide a valid value range for the DAY_OF_MONTH field.
     */
    @Test(timeout = 4000)
    public void range_forDayOfMonth_returnsNonNullValueRange() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();

        ValueRange dayOfMonthRange = chronology.INSTANCE.range(ChronoField.DAY_OF_MONTH);

        assertNotNull(dayOfMonthRange);
    }
}
