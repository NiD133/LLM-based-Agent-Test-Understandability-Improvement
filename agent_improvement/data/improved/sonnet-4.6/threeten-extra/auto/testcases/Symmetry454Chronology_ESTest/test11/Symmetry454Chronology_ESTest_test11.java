package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test11 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the value range for ALIGNED_DAY_OF_WEEK_IN_MONTH
     * on the Symmetry454 chronology returns a non-null ValueRange.
     *
     * In the Symmetry454 calendar, every month begins on Monday, so the
     * aligned day-of-week-in-month always maps to a valid 1–7 range.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Arrange
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;
        ChronoField alignedDayOfWeekInMonth = ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;

        // Act
        ValueRange alignedDayOfWeekInMonthRange = chronology.range(alignedDayOfWeekInMonth);

        // Assert: the chronology must return a valid (non-null) range for this field
        assertNotNull(alignedDayOfWeekInMonthRange);
    }
}
