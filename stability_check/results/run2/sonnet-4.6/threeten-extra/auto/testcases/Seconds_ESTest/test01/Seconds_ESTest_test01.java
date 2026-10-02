package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test01 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // 396 hours * 3600 seconds/hour = 1,425,600 seconds
        Seconds initialSeconds = Seconds.ofHours(396);

        // A small duration (396 seconds) to subtract from the larger amount
        Duration subtractionDuration = Duration.ofSeconds((long) 396);

        // Subtract: 1,425,600 - 396 = 1,425,204 seconds
        Seconds reducedSeconds = initialSeconds.minus((TemporalAmount) subtractionDuration);

        // Verify the subtracted result is correct
        assertEquals(1425204, reducedSeconds.getAmount());

        // Verify the two instances are distinct (original vs. reduced)
        boolean areEqual = initialSeconds.equals(reducedSeconds);
        assertFalse(reducedSeconds.equals((Object) initialSeconds));
        assertFalse(areEqual);

        // Verify the original Seconds object is unchanged (immutability)
        assertEquals(1425600, initialSeconds.getAmount());
    }
}
