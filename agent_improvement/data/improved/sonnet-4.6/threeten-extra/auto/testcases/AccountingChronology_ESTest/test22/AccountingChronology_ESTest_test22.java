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
public class AccountingChronology_ESTest_test22 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that dateYearDay(Era, int, int) throws ClassCastException when
     * the supplied Era is not an AccountingEra instance.
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.TUESDAY,
                Month.DECEMBER,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                1,
                1);

        Era incompatibleEra = ThaiBuddhistEra.BEFORE_BE;

        // dateYearDay must reject an Era that is not an AccountingEra
        try {
            chronology.dateYearDay(incompatibleEra, 1, 1);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            //
            // Era must be AccountingEra
            //
            verifyException("org.threeten.extra.chrono.AccountingChronology", e);
        }
    }
}
