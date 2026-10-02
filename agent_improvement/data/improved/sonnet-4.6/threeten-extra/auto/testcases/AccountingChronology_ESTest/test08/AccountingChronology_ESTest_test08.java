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
public class AccountingChronology_ESTest_test08 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Configuration shared by both chronology instances
        DayOfWeek yearEndDay = DayOfWeek.FRIDAY;
        Month fiscalYearEndMonth = Month.FEBRUARY;
        boolean endsInLastWeek = true;
        AccountingYearDivision quarterPattern = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 4;
        int yearOffset = -1;

        // Create two chronologies with identical parameters
        AccountingChronology chronologyA = AccountingChronology.create(
                yearEndDay, fiscalYearEndMonth, endsInLastWeek, quarterPattern, leapWeekInMonth, yearOffset);
        AccountingChronology chronologyB = AccountingChronology.create(
                yearEndDay, fiscalYearEndMonth, endsInLastWeek, quarterPattern, leapWeekInMonth, yearOffset);

        // Two instances built from the same configuration must be equal
        assertTrue(chronologyB.equals(chronologyA));
    }
}
