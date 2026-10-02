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

    @Test(timeout = 4000)
    public void test_range_alignedDayOfWeekInYear_returnsNonNull() throws Throwable {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;
        ValueRange alignedDayOfWeekInYearRange = chronology.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);
        assertNotNull(alignedDayOfWeekInYearRange);
    }
}
