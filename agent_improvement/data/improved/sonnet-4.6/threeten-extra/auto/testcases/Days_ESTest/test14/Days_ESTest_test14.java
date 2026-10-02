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
public class Days_ESTest_test14 extends Days_ESTest_scaffolding {

    /**
     * Verifies that multiplying a negative Days value by a negative scalar produces
     * a positive result, and that isPositive() reflects this correctly.
     *
     * Days.ofWeeks(-3386) = -23702 days (negative, so isPositive() == false).
     * Multiplying by -3386 gives -23702 * -3386 = 80254972 days (positive).
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // -3386 weeks = -23702 days (negative amount)
        Days negativeDays = Days.ofWeeks(-3386);

        // Multiplying two negatives yields a positive: -23702 * -3386 = 80254972
        Days productDays = negativeDays.multipliedBy(-3386);
        boolean productIsPositive = productDays.isPositive();

        assertEquals(80254972, productDays.getAmount());
        assertTrue(productIsPositive);
        assertFalse(negativeDays.isPositive());
    }
}
