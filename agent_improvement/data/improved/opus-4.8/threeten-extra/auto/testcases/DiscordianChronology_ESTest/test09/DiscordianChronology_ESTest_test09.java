package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test09 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the valid value range for the DAY_OF_WEEK field
     * returns a non-null ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeForDayOfWeekReturnsNonNullValueRange() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        ValueRange dayOfWeekRange = chronology.range(ChronoField.DAY_OF_WEEK);

        assertNotNull(dayOfWeekRange);
    }
}
