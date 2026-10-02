package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test26 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that converting Period.ZERO to Hours yields a zero-valued Hours instance
     * whose hashCode() is callable without error and whose isZero() returns true.
     */
    @Test(timeout = 4000)
    public void test_fromZeroPeriod_producesZeroHoursWithValidHashCode() throws Throwable {
        // Period.ZERO has no date-based components, so Hours.from should yield zero hours
        Hours zeroHours = Hours.from(Period.ZERO);

        // hashCode() must not throw and Hours with value 0 should hash consistently
        zeroHours.hashCode();

        assertTrue(zeroHours.isZero());
    }
}
