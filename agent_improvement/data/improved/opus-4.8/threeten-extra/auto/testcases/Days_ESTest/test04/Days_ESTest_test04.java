package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test04 extends Days_ESTest_scaffolding {

    /**
     * Verifies that subtracting a negative day-based duration yields a positive Days amount,
     * and that applying that amount to a Temporal produces a new (non-identical) Temporal.
     */
    @Test(timeout = 4000)
    public void subtractingNegativeDurationGivesPositiveDays() throws Throwable {
        // The start and end instants are identical, so there are zero days between them.
        Instant sameInstant = MockInstant.ofEpochSecond(-2086L, -2086L);
        Days zeroDays = Days.between(sameInstant, sameInstant);
        assertEquals(0, zeroDays.getAmount());

        // Subtracting a duration of -2086 days is equivalent to adding 2086 days: 0 - (-2086) = 2086.
        Duration negativeDuration = Duration.ofDays(-2086L);
        Days resultDays = zeroDays.minus((TemporalAmount) negativeDuration);
        assertEquals(2086, resultDays.getAmount());

        // Subtracting a non-zero amount from the instant must return a different Temporal instance.
        Temporal adjustedInstant = resultDays.subtractFrom(sameInstant);
        assertNotSame(adjustedInstant, sameInstant);
    }
}
