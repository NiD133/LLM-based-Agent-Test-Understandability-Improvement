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
public class InternationalFixedChronology_ESTest_test10 extends InternationalFixedChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_range_alignedWeekOfMonth_returnsNonNullValueRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange range = chronology.range(ChronoField.ALIGNED_WEEK_OF_MONTH);
        assertNotNull(range);
    }
}
