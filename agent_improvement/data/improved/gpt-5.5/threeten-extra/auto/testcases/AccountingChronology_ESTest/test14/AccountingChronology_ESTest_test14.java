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
public class AccountingChronology_ESTest_test14 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        DayOfWeek accountingYearEndsOn = DayOfWeek.WEDNESDAY;
        Month accountingYearEndMonth = Month.JUNE;
        AccountingYearDivision quarterPattern445 = AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS;

        AccountingChronology chronology = AccountingChronology.create(
                accountingYearEndsOn,
                accountingYearEndMonth,
                true,
                quarterPattern445,
                10,
                (-1));
        ChronoField requestedField = ChronoField.DAY_OF_MONTH;

        ValueRange dayOfMonthRange = chronology.range(requestedField);

        assertNotNull(dayOfMonthRange);
    }
}
