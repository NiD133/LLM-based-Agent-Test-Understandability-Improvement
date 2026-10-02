package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test26 extends Days_ESTest_scaffolding {

    /**
     * Verifies the arithmetic chain on {@link Days}:
     * subtracting a negative Duration adds days, and adding a Days amount to
     * itself doubles it.
     */
    @Test(timeout = 4000)
    public void subtractingNegativeDurationThenDoubling() throws Throwable {
        // Days between an instant and itself is zero.
        Instant sameInstant = MockInstant.ofEpochSecond(-2086L, -2086L);
        Days zeroDays = Days.between(sameInstant, sameInstant);
        assertTrue(zeroDays.isZero());

        // Subtracting -2086 days leaves 0 - (-2086) = 2086 days.
        Duration minusTwoThousandDays = Duration.ofDays(-2086L);
        Days twoThousandDays = zeroDays.minus((TemporalAmount) minusTwoThousandDays);

        // Adding the amount to itself doubles it: 2086 + 2086 = 4172.
        Days doubledDays = twoThousandDays.plus((TemporalAmount) twoThousandDays);
        assertEquals(4172, doubledDays.getAmount());
    }
}
