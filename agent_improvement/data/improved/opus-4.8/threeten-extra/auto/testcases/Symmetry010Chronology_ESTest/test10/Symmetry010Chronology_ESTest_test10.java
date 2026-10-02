package org.threeten.extra.chrono;

import static org.junit.Assert.assertNotNull;

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test10 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the chronology for the valid range of the
     * ALIGNED_WEEK_OF_MONTH field returns a (non-null) ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeOfAlignedWeekOfMonthIsDefined() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        ValueRange alignedWeekOfMonthRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_MONTH);

        assertNotNull(alignedWeekOfMonthRange);
    }
}
