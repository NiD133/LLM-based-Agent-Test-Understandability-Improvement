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
public class Hours_ESTest_test06 extends Hours_ESTest_scaffolding {

    private static final int NEGATIVE_HOURS_OFFSET = -1485;

    /**
     * Verifies that Hours.from(Period.ZERO) produces zero hours,
     * that adding a negative offset to zero hours yields that offset,
     * and that addTo() on an Instant returns a new Temporal instance
     * (because a non-zero Hours amount modifies the temporal).
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Convert a zero-length Period to Hours — expected result is 0 hours
        Hours zeroHours = Hours.from(Period.ZERO);

        // Add a negative offset to get Hours representing NEGATIVE_HOURS_OFFSET hours
        Hours negativeHours = zeroHours.plus(NEGATIVE_HOURS_OFFSET);

        // Create a reference Instant (epoch second and nano-of-second both use NEGATIVE_HOURS_OFFSET)
        Instant referenceInstant = MockInstant.ofEpochSecond((long) NEGATIVE_HOURS_OFFSET, (long) NEGATIVE_HOURS_OFFSET);

        // addTo() must return a distinct Temporal instance because the amount is non-zero
        Temporal adjustedInstant = negativeHours.addTo(referenceInstant);
        assertNotSame(adjustedInstant, referenceInstant);

        // The Hours amount must reflect the value that was added
        assertEquals(NEGATIVE_HOURS_OFFSET, negativeHours.getAmount());
    }
}
