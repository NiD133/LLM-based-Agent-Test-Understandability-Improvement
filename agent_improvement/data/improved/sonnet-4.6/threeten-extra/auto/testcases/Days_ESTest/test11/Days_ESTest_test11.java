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
public class Days_ESTest_test11 extends Days_ESTest_scaffolding {

    /**
     * Verifies that dividing a zero-day span by any non-zero divisor still yields zero,
     * and that the same Days.ZERO singleton is returned (identity preserved).
     *
     * Days.between(t, t) == 0 days; 0 / (-507) == 0 days (same instance).
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // A fixed instant used as both start and end so the span between them is zero days
        Instant referenceInstant = MockInstant.ofEpochSecond((-2086L), (-2086L));

        // Days between an instant and itself is always zero
        Days zeroDays = Days.between(referenceInstant, referenceInstant);

        // Dividing zero by any non-zero divisor is still zero; Days returns the same singleton
        Days zeroDaysDividedByNegative = zeroDays.dividedBy((-507));

        assertTrue(zeroDaysDividedByNegative.isZero());
        // Days.ZERO singleton is reused — dividing zero days must return the identical object
        assertSame(zeroDaysDividedByNegative, zeroDays);
    }
}
