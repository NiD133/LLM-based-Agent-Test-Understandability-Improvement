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
public class Symmetry454Chronology_ESTest_test04 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that the chronology returns a valid ValueRange for the
     * MONTH_OF_YEAR field rather than null.
     */
    @Test(timeout = 4000)
    public void rangeForMonthOfYearIsNotNull() throws Throwable {
        ValueRange monthOfYearRange = Symmetry454Chronology.INSTANCE.range(ChronoField.MONTH_OF_YEAR);

        assertNotNull(monthOfYearRange);
    }
}
