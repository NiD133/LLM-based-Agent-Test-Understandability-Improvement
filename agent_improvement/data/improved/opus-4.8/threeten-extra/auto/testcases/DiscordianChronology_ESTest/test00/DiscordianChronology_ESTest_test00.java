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
public class DiscordianChronology_ESTest_test00 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * The Discordian chronology only customises the value range for a fixed set of
     * date-based fields (day-of-month, month-of-year, year, ...). For any other field,
     * {@link DiscordianChronology#range(ChronoField)} falls back to the field's own range.
     * MINUTE_OF_DAY is a time-based field that is not special-cased, so the call must
     * still return a non-null range (the default one supplied by the field itself).
     */
    @Test(timeout = 4000)
    public void rangeForUnhandledTimeFieldReturnsFieldDefaultRange() throws Throwable {
        DiscordianChronology chronology = DiscordianChronology.INSTANCE;

        ValueRange minuteOfDayRange = chronology.range(ChronoField.MINUTE_OF_DAY);

        assertNotNull(minuteOfDayRange);
    }
}
