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
public class Seconds_ESTest_test06 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that ofMinutes() converts minutes to seconds correctly,
     * and that addTo() returns a new Temporal instance (not the original).
     *
     * -36 minutes * 60 = -2160 seconds.
     * Adding a non-zero Seconds amount to an Instant must produce a new object.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // -36 minutes = -2160 seconds
        Seconds negativeThirtySixMinutes = Seconds.ofMinutes(-36);

        // An instant slightly before the Unix epoch (epoch millis = -36)
        Instant baseInstant = MockInstant.ofEpochMilli(-36);

        // Adding -2160 seconds to the instant should return a shifted instant
        Temporal adjustedInstant = negativeThirtySixMinutes.addTo(baseInstant);

        // ofMinutes(-36) must store exactly -2160 seconds
        assertEquals(-2160, negativeThirtySixMinutes.getAmount());

        // addTo() must return a new object, not the original instant
        assertNotSame(adjustedInstant, baseInstant);
    }
}
