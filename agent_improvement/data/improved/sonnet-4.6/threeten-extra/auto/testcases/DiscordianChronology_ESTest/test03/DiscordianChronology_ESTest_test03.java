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
public class DiscordianChronology_ESTest_test03 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the valid range for PROLEPTIC_MONTH on a
     * DiscordianChronology returns a non-null ValueRange, confirming the
     * chronology supports that field.
     */
    @Test(timeout = 4000)
    public void testRangeForProlepticMonthIsNotNull() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        ValueRange prolepticMonthRange = chronology.range(ChronoField.PROLEPTIC_MONTH);
        assertNotNull(prolepticMonthRange);
    }
}
