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
public class InternationalFixedChronology_ESTest_test09 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * The chronology should provide a valid value range for the
     * ALIGNED_WEEK_OF_YEAR field rather than returning null.
     */
    @Test(timeout = 4000)
    public void rangeForAlignedWeekOfYearIsNotNull() throws Throwable {
        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;

        ValueRange alignedWeekOfYearRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_YEAR);

        assertNotNull(alignedWeekOfYearRange);
    }
}
