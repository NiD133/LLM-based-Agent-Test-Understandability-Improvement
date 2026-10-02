package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test04 extends Hours_ESTest_scaffolding {

    /**
     * Subtracting a negative number of hours from {@link Hours#ZERO} yields the
     * positive amount, and applying that amount via {@code subtractFrom} produces
     * a new {@link Temporal} (the original instant is left unchanged).
     */
    @Test(timeout = 4000)
    public void subtractingNegativeHoursThenSubtractFromInstant() throws Throwable {
        // Hours.ZERO.minus(-18) == Hours.of(18)
        Hours eighteenHours = Hours.ZERO.minus(-18);
        assertEquals(18, eighteenHours.getAmount());

        // Subtract the 18-hour amount from an arbitrary instant.
        Instant instant = MockInstant.ofEpochSecond(-2893L, -2893L);
        Temporal adjustedInstant = eighteenHours.subtractFrom(instant);

        // A non-zero amount produces a new, distinct temporal object.
        assertNotSame(adjustedInstant, instant);
    }
}
