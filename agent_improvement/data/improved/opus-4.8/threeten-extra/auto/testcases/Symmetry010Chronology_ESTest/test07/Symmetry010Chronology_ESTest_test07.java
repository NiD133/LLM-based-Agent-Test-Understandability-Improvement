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
public class Symmetry010Chronology_ESTest_test07 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the chronology for the valid range of the
     * DAY_OF_YEAR field returns a (non-null) ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeForDayOfYearReturnsValueRange() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        ValueRange dayOfYearRange = chronology.range(ChronoField.DAY_OF_YEAR);

        assertNotNull(dayOfYearRange);
    }
}
