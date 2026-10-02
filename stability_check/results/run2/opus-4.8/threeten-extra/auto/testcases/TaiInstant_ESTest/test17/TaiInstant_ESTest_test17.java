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

    /**
     * Verifies that computing the duration from an instant to itself leaves the
     * original instant unchanged (TaiInstant is immutable), so its TAI seconds
     * and nano-of-second still hold the values it was created with.
     */
    @Test(timeout = 4000)
    public void durationUntilSelfLeavesInstantUnchanged() throws Throwable {
        long taiSeconds = 86400L;
        long nanoAdjustment = 0L;
        TaiInstant instant = TaiInstant.ofTaiSeconds(taiSeconds, nanoAdjustment);

        instant.durationUntil(instant);

        assertEquals(86400L, instant.getTaiSeconds());
        assertEquals(0, instant.getNano());
    }
}
