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
     * Verifies that calling durationUntil with the instant itself does not
     * mutate the instant: TaiInstant is immutable, so its TAI seconds and
     * nano-of-second remain unchanged after the call.
     */
    @Test(timeout = 4000)
    public void durationUntilLeavesInstantUnchanged() throws Throwable {
        long taiSeconds = 86400L;
        TaiInstant instant = TaiInstant.ofTaiSeconds(taiSeconds, 0L);

        instant.durationUntil(instant);

        assertEquals(taiSeconds, instant.getTaiSeconds());
        assertEquals(0, instant.getNano());
    }
}
