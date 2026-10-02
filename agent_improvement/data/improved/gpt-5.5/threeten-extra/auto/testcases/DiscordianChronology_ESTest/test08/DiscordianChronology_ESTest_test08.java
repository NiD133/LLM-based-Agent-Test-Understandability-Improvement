package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test08 extends DiscordianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        ChronoField alignedWeekOfMonth = ChronoField.ALIGNED_WEEK_OF_MONTH;

        ValueRange alignedWeekRange = chronology.range(alignedWeekOfMonth);

        assertNotNull(alignedWeekRange);
    }
}
