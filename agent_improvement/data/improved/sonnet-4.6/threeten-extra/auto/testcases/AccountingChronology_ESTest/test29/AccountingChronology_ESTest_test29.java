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
public class AccountingChronology_ESTest_test29 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Tests that dateNow(ZoneId) returns a non-null AccountingDate when called with ZoneOffset.MAX.
     * The chronology is configured for a fiscal year ending on the Sunday nearest to the end of February,
     * divided into quarters using the 4-4-5 week pattern, with the leap week placed in period 4.
     */
    @Test(timeout = 4000)
    public void test29() throws Throwable {
        DayOfWeek yearEndDay = DayOfWeek.SUNDAY;
        Month yearEndMonth = Month.FEBRUARY;
        // Year ends nearest the end of February (not restricted to last week)
        boolean inLastWeek = false;
        AccountingYearDivision quarterPattern = AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS;
        int leapWeekInPeriod = 4;
        int yearOffset = 4;

        AccountingChronology fiscalChronology = AccountingChronology.create(
                yearEndDay, yearEndMonth, inLastWeek, quarterPattern, leapWeekInPeriod, yearOffset);

        // ZoneOffset.MAX is +18:00, the maximum valid UTC offset
        ZoneOffset maxZoneOffset = ZoneOffset.MAX;
        AccountingDate today = fiscalChronology.dateNow((ZoneId) maxZoneOffset);

        assertNotNull(today);
    }
}
