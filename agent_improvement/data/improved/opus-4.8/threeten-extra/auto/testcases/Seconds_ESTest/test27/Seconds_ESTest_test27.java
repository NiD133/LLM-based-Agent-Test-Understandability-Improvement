package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test27 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that {@code ofMinutes} converts negative minutes into the
     * equivalent (negative) number of seconds, and that converting the amount
     * to a {@link Duration} leaves the original amount unchanged.
     */
    @Test(timeout = 4000)
    public void ofMinutes_negativeMinutes_convertsToSecondsAndIsImmutable() throws Throwable {
        int minutes = -19;
        int expectedSeconds = minutes * 60; // -1140 seconds

        Seconds nineteenMinutesAgo = Seconds.ofMinutes(minutes);

        // toDuration() must not modify the underlying Seconds amount.
        Duration duration = nineteenMinutesAgo.toDuration();

        assertEquals(expectedSeconds, nineteenMinutesAgo.getAmount());
    }
}
