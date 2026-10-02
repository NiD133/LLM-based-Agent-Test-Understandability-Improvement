package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.chrono.JapaneseEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test19 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that dateEpochDay returns a non-null BritishCutoverDate
     * for a positive epoch day value (epoch day 3697 corresponds to a date
     * well after the Gregorian cutover in 1752, so it uses the ISO/Gregorian rules).
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // Instantiate via the deprecated constructor (as generated) to access INSTANCE
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        // Convert epoch day 3697 (days since 1970-01-01) to a BritishCutoverDate
        BritishCutoverDate date = chronology.INSTANCE.dateEpochDay(3697L);

        assertNotNull(date);
    }
}
