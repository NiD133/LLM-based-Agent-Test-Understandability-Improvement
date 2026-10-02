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
public class AccountingChronology_ESTest_test03 extends AccountingChronology_ESTest_scaffolding {

    // Two AccountingChronology instances that share all parameters except leapWeekInMonth
    // are not considered equal, because leapWeekInMonth is part of the equality contract.
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        DayOfWeek endDay = DayOfWeek.SATURDAY;
        Month endMonth = Month.NOVEMBER;
        AccountingYearDivision division = AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS;
        int yearOffset = 4;

        AccountingChronology chronologyLeapWeekInMonth8 = AccountingChronology.create(endDay, endMonth, true, division, 8, yearOffset);
        AccountingChronology chronologyLeapWeekInMonth4 = AccountingChronology.create(endDay, endMonth, true, division, 4, yearOffset);

        assertFalse(chronologyLeapWeekInMonth4.equals(chronologyLeapWeekInMonth8));
        assertFalse(chronologyLeapWeekInMonth8.equals((Object) chronologyLeapWeekInMonth4));
    }
}
