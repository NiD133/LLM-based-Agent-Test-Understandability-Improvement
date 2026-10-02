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
public class InternationalFixedChronology_ESTest_test11 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the DAY_OF_WEEK range on the International Fixed chronology
     * returns a valid (non-null) ValueRange. In the International Fixed calendar, week days
     * have a special range [0, 7] because Year Day and Leap Day are not part of any week,
     * so the minimum can be 0 (indicating a day outside the regular weekly structure).
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ChronoField dayOfWeekField = ChronoField.DAY_OF_WEEK;

        ValueRange dayOfWeekRange = chronology.range(dayOfWeekField);

        assertNotNull(dayOfWeekRange);
    }
}
