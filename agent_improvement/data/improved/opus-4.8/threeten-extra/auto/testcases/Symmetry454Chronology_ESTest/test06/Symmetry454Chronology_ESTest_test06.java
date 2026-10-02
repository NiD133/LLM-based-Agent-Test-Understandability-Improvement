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
public class Symmetry454Chronology_ESTest_test06 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the Symmetry454 chronology for the valid range of
     * the DAY_OF_YEAR field returns a (non-null) ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeOfDayOfYearFieldIsNotNull() throws Throwable {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        ValueRange dayOfYearRange = chronology.range(ChronoField.DAY_OF_YEAR);

        assertNotNull(dayOfYearRange);
    }
}
