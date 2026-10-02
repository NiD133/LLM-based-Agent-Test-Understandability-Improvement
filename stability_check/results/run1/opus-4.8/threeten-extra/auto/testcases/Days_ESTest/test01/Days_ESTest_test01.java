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
public class Days_ESTest_test01 extends Days_ESTest_scaffolding {

    /**
     * Multiplying a negative Days amount by a negative scalar yields a positive
     * amount that differs from the original, and equality is not affected by
     * argument order.
     */
    @Test(timeout = 4000)
    public void multiplyingNegativeDaysByNegativeScalarGivesDifferentPositiveAmount() throws Throwable {
        // -3386 weeks == -3386 * 7 == -23702 days
        Days negativeDays = Days.ofWeeks(-3386);

        // -23702 * -3386 == 80254972 days
        Days multipliedDays = negativeDays.multipliedBy(-3386);

        // The original and the multiplied result hold different amounts.
        assertFalse(negativeDays.equals(multipliedDays));
        assertFalse(multipliedDays.equals((Object) negativeDays));

        // The starting amount was negative, so it is not positive.
        assertFalse(negativeDays.isPositive());
        assertEquals(80254972, multipliedDays.getAmount());
    }
}
