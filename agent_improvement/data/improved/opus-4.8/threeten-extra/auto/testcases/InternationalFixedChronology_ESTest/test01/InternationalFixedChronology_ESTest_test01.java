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
public class InternationalFixedChronology_ESTest_test01 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the chronology for the value range of a standard
     * time field (HOUR_OF_DAY) returns a non-null range. The International Fixed
     * chronology has no special handling for HOUR_OF_DAY, so it falls back to the
     * field's own default range.
     */
    @Test(timeout = 4000)
    public void rangeForHourOfDayReturnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        ValueRange hourOfDayRange = chronology.range(ChronoField.HOUR_OF_DAY);

        assertNotNull(hourOfDayRange);
    }
}
