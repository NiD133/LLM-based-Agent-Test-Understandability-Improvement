package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test05 extends BritishCutoverChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_range_alignedWeekOfYear_returnsNonNullValueRange() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        // ALIGNED_WEEK_OF_YEAR has a custom range in BritishCutoverChronology (1..51..53)
        ValueRange alignedWeekOfYearRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_YEAR);

        assertNotNull(alignedWeekOfYearRange);
    }
}
