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
public class Symmetry454Chronology_ESTest_test13 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the Symmetry454 chronology for the valid value range
     * of the ALIGNED_DAY_OF_WEEK_IN_YEAR field returns a non-null ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeForAlignedDayOfWeekInYearReturnsNonNull() throws Throwable {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        ValueRange range = chronology.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);

        assertNotNull(range);
    }
}
