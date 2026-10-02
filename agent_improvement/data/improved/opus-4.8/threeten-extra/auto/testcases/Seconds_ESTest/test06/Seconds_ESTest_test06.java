package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
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
     * Verifies that adding a Seconds amount to an Instant returns a new,
     * distinct Temporal, and that ofMinutes converts minutes to seconds.
     */
    @Test(timeout = 4000)
    public void addToInstantReturnsNewTemporalAndConvertsMinutesToSeconds() throws Throwable {
        // -36 minutes converts to -36 * 60 = -2160 seconds.
        Seconds minus36Minutes = Seconds.ofMinutes(-36);
        Instant baseInstant = MockInstant.ofEpochMilli(-36);

        Temporal shiftedInstant = minus36Minutes.addTo(baseInstant);

        assertEquals(-2160, minus36Minutes.getAmount());
        // addTo produces a new Temporal instance rather than mutating the input.
        assertNotSame(shiftedInstant, baseInstant);
    }
}
