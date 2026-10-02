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
public class DiscordianChronology_ESTest_test07 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the value range for ALIGNED_WEEK_OF_YEAR returns a non-null result,
     * confirming that the Discordian chronology supports this field and has a defined range for it.
     */
    @Test(timeout = 4000)
    public void test07_rangeForAlignedWeekOfYear_returnsNonNullValueRange() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        ChronoField alignedWeekOfYear = ChronoField.ALIGNED_WEEK_OF_YEAR;

        ValueRange weekOfYearRange = chronology.range(alignedWeekOfYear);

        assertNotNull(weekOfYearRange);
    }
}
