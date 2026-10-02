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
public class InternationalFixedChronology_ESTest_test05 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the chronology for the valid range of the
     * MONTH_OF_YEAR field returns a (non-null) ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeOfMonthOfYearFieldIsNotNull() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        ValueRange monthOfYearRange = chronology.range(ChronoField.MONTH_OF_YEAR);

        assertNotNull(monthOfYearRange);
    }
}
