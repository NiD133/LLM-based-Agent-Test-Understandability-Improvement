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
public class Symmetry454Chronology_ESTest_test11 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that the Symmetry454 chronology provides a valid range
     * for the ALIGNED_DAY_OF_WEEK_IN_MONTH field.
     */
    @Test(timeout = 4000)
    public void range_forAlignedDayOfWeekInMonth_returnsValueRange() throws Throwable {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        ValueRange alignedDayOfWeekInMonthRange =
                chronology.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);

        assertNotNull(alignedDayOfWeekInMonthRange);
    }
}
