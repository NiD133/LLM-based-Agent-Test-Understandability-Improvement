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
public class AccountingChronology_ESTest_test30 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        AccountingYearDivision accountingYearDivision0 = AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS;
        DayOfWeek dayOfWeek0 = DayOfWeek.SUNDAY;
        Month month0 = Month.JANUARY;
        AccountingChronology accountingChronology0 = AccountingChronology.create(dayOfWeek0, month0, false, accountingYearDivision0, 12, 12);
        // Undeclared exception!
        try {
            accountingChronology0.zonedDateTime((TemporalAccessor) month0);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Unable to obtain ChronoZonedDateTime from TemporalAccessor: class java.time.Month
            //
            verifyException("java.time.chrono.Chronology", e);
        }
    }
}
