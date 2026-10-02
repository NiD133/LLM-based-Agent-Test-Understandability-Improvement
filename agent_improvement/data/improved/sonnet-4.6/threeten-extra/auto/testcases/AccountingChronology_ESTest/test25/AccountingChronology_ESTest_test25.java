package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test25 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology always exposes exactly two eras (BCE and CE),
     * regardless of the specific calendar configuration used to create it.
     */
    @Test(timeout = 4000)
    public void test_eras_returnsExactlyTwoEras() throws Throwable {
        // Build an AccountingChronology using a 4-5-4 quarter division ending on Wednesdays nearest July
        DayOfWeek yearEndsOnWednesday = DayOfWeek.WEDNESDAY;
        Month yearEndsNearJuly = Month.JULY;
        AccountingYearDivision quartersWith454Pattern = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth4 = 4;
        int yearOffset = -1442;
        AccountingChronology chronology = AccountingChronology.create(
                yearEndsOnWednesday, yearEndsNearJuly, false, quartersWith454Pattern, leapWeekInMonth4, yearOffset);

        // The Accounting calendar always has two eras: BCE and CE
        List<Era> eras = chronology.eras();
        assertEquals("AccountingChronology should have exactly two eras (BCE and CE)", 2, eras.size());
    }
}
