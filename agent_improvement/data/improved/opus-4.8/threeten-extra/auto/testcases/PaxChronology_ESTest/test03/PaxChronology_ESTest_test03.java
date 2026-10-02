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
public class PaxChronology_ESTest_test03 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link PaxChronology#range(ChronoField)} returns a value range
     * for the ALIGNED_WEEK_OF_YEAR field rather than null.
     */
    @Test(timeout = 4000)
    public void rangeForAlignedWeekOfYearIsNotNull() throws Throwable {
        PaxChronology paxChronology = new PaxChronology();

        ValueRange alignedWeekOfYearRange = paxChronology.range(ChronoField.ALIGNED_WEEK_OF_YEAR);

        assertNotNull(alignedWeekOfYearRange);
    }
}
