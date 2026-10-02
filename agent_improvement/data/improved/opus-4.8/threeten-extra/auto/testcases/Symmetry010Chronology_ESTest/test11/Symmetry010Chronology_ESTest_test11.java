package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test11 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that {@link Symmetry010Chronology#range(ChronoField)} returns a
     * valid (non-null) value range for the DAY_OF_WEEK field.
     */
    @Test(timeout = 4000)
    public void rangeForDayOfWeekIsNotNull() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        ValueRange dayOfWeekRange = chronology.range(ChronoField.DAY_OF_WEEK);

        assertNotNull(dayOfWeekRange);
    }
}
