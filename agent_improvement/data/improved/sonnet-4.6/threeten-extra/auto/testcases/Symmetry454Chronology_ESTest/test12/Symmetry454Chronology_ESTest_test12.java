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
public class Symmetry454Chronology_ESTest_test12 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that applying a Symmetry454Date as its own TemporalAdjuster
     * returns an equal date (i.e., adjusting a date with itself is idempotent).
     */
    @Test(timeout = 4000)
    public void test_adjustingDateWithItselfReturnsEqualDate() throws Throwable {
        // Arrange: create a Symmetry454 date for year 7, month 1, day 7
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;
        Symmetry454Date originalDate = chronology.date(7, 1, 7);

        // Act: use the date itself as the TemporalAdjuster
        Symmetry454Date adjustedDate = originalDate.with((TemporalAdjuster) originalDate);

        // Assert: the adjusted date equals the original date
        assertTrue(adjustedDate.equals((Object) originalDate));
    }
}
