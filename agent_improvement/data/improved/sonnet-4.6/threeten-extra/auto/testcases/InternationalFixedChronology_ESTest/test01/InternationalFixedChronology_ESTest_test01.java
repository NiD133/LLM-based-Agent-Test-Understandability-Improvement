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
public class InternationalFixedChronology_ESTest_test01 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the range for a time-of-day field (HOUR_OF_DAY),
     * which is not explicitly handled by the IFC chronology's switch statement,
     * falls through to the default case and returns a non-null ValueRange
     * by delegating to the ChronoField itself.
     */
    @Test(timeout = 4000)
    public void test_range_forUnhandledTimeField_delegatesToChronoField() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange hourOfDayRange = chronology.range(ChronoField.HOUR_OF_DAY);
        assertNotNull(hourOfDayRange);
    }
}
