package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.JapaneseEra;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test13 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Tests that applying an EthiopicDate as a TemporalAdjuster to an InternationalFixedDate
     * produces the correct epoch day and year length.
     *
     * The source date is year 7, day-of-year 7 in the International Fixed calendar.
     * Converting to EthiopicDate and applying it back as an adjuster yields a date
     * with epoch day -716965 (deep in BCE) and a standard 365-day year.
     */
    @Test(timeout = 4000)
    public void test_withEthiopicDateAdjuster_yieldsCorrectEpochDayAndYearLength() throws Throwable {
        // Create a date at year 7, day 7 of that year in the International Fixed calendar
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        InternationalFixedDate sourceDate = chronology.dateYearDay(7, 7);

        // Convert the International Fixed date to its Ethiopic calendar equivalent
        EthiopicDate ethiopicEquivalent = EthiopicDate.from(sourceDate);

        // Apply the Ethiopic date back as a TemporalAdjuster to the original International Fixed date
        InternationalFixedDate adjustedDate = sourceDate.with((TemporalAdjuster) ethiopicEquivalent);

        // The adjusted date should map to epoch day -716965 (far before the Unix epoch)
        assertEquals((-716965L), adjustedDate.toEpochDay());
        // Year 7 of the International Fixed calendar is not a leap year, so it has 365 days
        assertEquals(365, adjustedDate.lengthOfYear());
    }
}
