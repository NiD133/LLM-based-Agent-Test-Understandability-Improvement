package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test31 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology can build a zoned date-time from an
     * Instant and a ZoneId, returning a non-null ChronoZonedDateTime.
     */
    @Test(timeout = 4000)
    public void zonedDateTimeFromInstantAndZoneReturnsNonNull() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.FEBRUARY,
                false,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                1,
                1);

        Instant instant = MockInstant.ofEpochSecond(1L);
        ZoneId zone = ZoneOffset.ofTotalSeconds(1);

        ChronoZonedDateTime<AccountingDate> zonedDateTime =
                chronology.zonedDateTime(instant, zone);

        assertNotNull(zonedDateTime);
    }
}
