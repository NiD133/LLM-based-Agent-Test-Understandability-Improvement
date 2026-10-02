package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test10 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the value range for ALIGNED_WEEK_OF_MONTH returns a non-null result.
     * In the Symmetry010 calendar, months have either 4 weeks (standard) or 5 weeks (long months),
     * so the aligned-week-of-month range spans 1..4 to 1..5.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        ChronoField alignedWeekOfMonth = ChronoField.ALIGNED_WEEK_OF_MONTH;

        ValueRange weekOfMonthRange = chronology.range(alignedWeekOfMonth);

        assertNotNull(weekOfMonthRange);
    }
}
