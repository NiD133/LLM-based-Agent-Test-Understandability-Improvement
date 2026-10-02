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
public class InternationalFixedChronology_ESTest_test02 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the chronology for the valid value range of the
     * YEAR field returns a (non-null) range.
     */
    @Test(timeout = 4000)
    public void rangeForYearFieldReturnsRange() throws Throwable {
        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;

        ValueRange yearRange = chronology.range(ChronoField.YEAR);

        assertNotNull(yearRange);
    }
}
