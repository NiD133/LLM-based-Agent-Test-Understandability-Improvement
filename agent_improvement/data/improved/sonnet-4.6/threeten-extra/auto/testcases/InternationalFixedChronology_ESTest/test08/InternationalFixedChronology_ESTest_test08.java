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
public class InternationalFixedChronology_ESTest_test08 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the DAY_OF_MONTH field range on the chronology
     * returns a non-null ValueRange.
     */
    @Test(timeout = 4000)
    public void test_range_dayOfMonth_returnsNonNullValueRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange dayOfMonthRange = chronology.range(ChronoField.DAY_OF_MONTH);
        assertNotNull(dayOfMonthRange);
    }
}
