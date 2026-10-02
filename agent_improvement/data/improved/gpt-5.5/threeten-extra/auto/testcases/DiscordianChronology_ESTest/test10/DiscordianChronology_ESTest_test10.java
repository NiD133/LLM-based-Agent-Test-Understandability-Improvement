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
public class DiscordianChronology_ESTest_test10 extends DiscordianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        DiscordianDate currentDiscordianDate = DiscordianDate.now();
        DiscordianChronology chronology = currentDiscordianDate.getChronology();
        ChronoField alignedDayOfWeekInMonth = ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;

        ValueRange range = chronology.range(alignedDayOfWeekInMonth);

        assertNotNull(range);
    }
}
