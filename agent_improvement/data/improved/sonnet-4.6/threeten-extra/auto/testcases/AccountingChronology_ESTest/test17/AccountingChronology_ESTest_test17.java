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
public class AccountingChronology_ESTest_test17 extends AccountingChronology_ESTest_scaffolding {

    /**
     * BCE year-of-era 1 should map to proleptic year 0.
     * The formula for BCE is: prolepticYear = 1 - yearOfEra, so yearOfEra=1 gives 0.
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        DayOfWeek endsOnThursday = DayOfWeek.THURSDAY;
        Month endsInJanuary = Month.JANUARY;
        AccountingYearDivision thirteenEvenMonths = AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS;
        AccountingChronology chronology = AccountingChronology.create(
                endsOnThursday, endsInJanuary, true, thirteenEvenMonths, 1, 1);

        int prolepticYear = chronology.prolepticYear(AccountingEra.BCE, 1);

        assertEquals(0, prolepticYear);
    }
}
