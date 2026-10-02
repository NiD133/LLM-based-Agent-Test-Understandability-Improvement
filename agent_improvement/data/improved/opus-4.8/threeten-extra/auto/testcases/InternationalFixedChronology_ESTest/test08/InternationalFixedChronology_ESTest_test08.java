package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test08 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the chronology for the valid range of the
     * DAY_OF_MONTH field returns a (non-null) ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeForDayOfMonthReturnsValueRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        ValueRange dayOfMonthRange = chronology.range(ChronoField.DAY_OF_MONTH);

        assertNotNull(dayOfMonthRange);
    }
}
