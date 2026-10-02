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
public class AccountingChronology_ESTest_test31 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that zonedDateTime(Instant, ZoneId) returns a non-null ChronoZonedDateTime
     * when given a valid instant and zone offset, using a 4-5-4 quarterly accounting chronology
     * that ends on Wednesdays nearest to the end of February.
     */
    @Test(timeout = 4000)
    public void test31() throws Throwable {
        // Configure an accounting chronology ending on Wednesdays nearest to February year-end,
        // divided into 4-5-4 week quarters with the leap week in month 1, starting in ISO year 1.
        DayOfWeek yearEndsOnWednesday = DayOfWeek.WEDNESDAY;
        Month fiscalYearEndMonth = Month.FEBRUARY;
        boolean endsInLastWeek = false; // ends nearest to (not strictly within) the last week
        AccountingYearDivision quarterlyPattern = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 1;
        int yearOffset = 1;
        AccountingChronology chronology = AccountingChronology.create(
                yearEndsOnWednesday, fiscalYearEndMonth, endsInLastWeek,
                quarterlyPattern, leapWeekInMonth, yearOffset);

        // Use an instant just after the Unix epoch and a zone offset of +1 second
        Instant oneSecondAfterEpoch = MockInstant.ofEpochSecond((long) 1);
        ZoneOffset oneTotalSecondOffset = ZoneOffset.ofTotalSeconds(1);

        // Converting the instant to an accounting zoned date-time should succeed
        ChronoZonedDateTime<AccountingDate> zonedDateTime =
                chronology.zonedDateTime(oneSecondAfterEpoch, (ZoneId) oneTotalSecondOffset);
        assertNotNull(zonedDateTime);
    }
}
