package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test17 extends TaiInstant_ESTest_scaffolding {

    // 86400 seconds = exactly one day (24 * 60 * 60)
    private static final long SECONDS_PER_DAY = 86400L;

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        // Create a TAI instant at exactly one day after the TAI epoch, with no nanosecond offset
        TaiInstant taiInstant0 = TaiInstant.ofTaiSeconds(SECONDS_PER_DAY, 0L);

        // Computing the duration from an instant to itself should return zero duration;
        // calling durationUntil must not mutate the original instant
        taiInstant0.durationUntil(taiInstant0);

        // Verify the instant is unchanged (immutability) after durationUntil call
        assertEquals(SECONDS_PER_DAY, taiInstant0.getTaiSeconds());
        assertEquals(0, taiInstant0.getNano());
    }
}
