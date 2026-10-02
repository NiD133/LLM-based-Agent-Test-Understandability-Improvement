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
public class InternationalFixedChronology_ESTest_test15 extends InternationalFixedChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        ZoneOffset minimumZoneOffset = ZoneOffset.MIN;
        InternationalFixedDate currentDateAtMinimumOffset = InternationalFixedDate.now((ZoneId) minimumZoneOffset);
        InternationalFixedChronology chronology = currentDateAtMinimumOffset.getChronology();

        boolean isCenturyYearLeap = chronology.isLeapYear(100L);

        assertEquals("The current mocked International Fixed date should be in a common year",
                365, currentDateAtMinimumOffset.lengthOfYear());
        assertFalse("Year 100 is not a leap year in the International Fixed chronology",
                isCenturyYearLeap);
    }
}
