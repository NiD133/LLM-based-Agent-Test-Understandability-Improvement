package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test17 extends Days_ESTest_scaffolding {

    // -3574 weeks * 7 days/week = -25018 days
    private static final int WEEKS = -3574;
    private static final int EXPECTED_DAYS = -25018;

    @Test(timeout = 4000)
    public void test_ofWeeks_negativeInput_isNotZeroAndHasCorrectDayCount() throws Throwable {
        Days days = Days.ofWeeks(WEEKS);

        assertFalse("Days created from a non-zero week count should not be zero", days.isZero());
        assertEquals("Days amount should equal weeks multiplied by 7", EXPECTED_DAYS, days.getAmount());
    }
}
