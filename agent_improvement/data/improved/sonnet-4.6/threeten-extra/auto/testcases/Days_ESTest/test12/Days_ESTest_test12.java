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
public class Days_ESTest_test12 extends Days_ESTest_scaffolding {

    /**
     * Verifies that Days.between() on the same instant yields zero days,
     * and that multiplying zero days by 1 still yields zero days.
     */
    @Test(timeout = 4000)
    public void test_daysBetweenSameInstant_multipliedByOne_isZero() throws Throwable {
        // An arbitrary instant used as both start and end
        Instant sameInstant = MockInstant.ofEpochSecond((-2086L), (-2086L));

        // Days between an instant and itself must be zero
        Days zeroDays = Days.between(sameInstant, sameInstant);

        // Multiplying zero days by 1 should still be zero
        Days result = zeroDays.multipliedBy(1);

        assertEquals(0, result.getAmount());
    }
}
