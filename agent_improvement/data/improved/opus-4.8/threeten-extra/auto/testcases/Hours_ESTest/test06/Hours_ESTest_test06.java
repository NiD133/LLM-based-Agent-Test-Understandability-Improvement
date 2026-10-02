package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test06 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that adding a negative Hours amount to an Instant produces a new
     * Temporal instance (immutability), while the original Hours amount is unchanged.
     */
    @Test(timeout = 4000)
    public void addingNegativeHoursToInstantReturnsNewTemporal() throws Throwable {
        // Start from a zero-length period and convert it to Hours (zero hours).
        Hours zeroHours = Hours.from(Period.ZERO);

        // Subtract 1485 hours by adding a negative amount.
        Hours negativeHours = zeroHours.plus(-1485);
        assertEquals(-1485, negativeHours.getAmount());

        // Adding the amount to an Instant must yield a distinct Temporal object.
        Instant instant = MockInstant.ofEpochSecond(-1485L, -1485L);
        Temporal adjustedInstant = negativeHours.addTo(instant);
        assertNotSame(adjustedInstant, instant);
    }
}
