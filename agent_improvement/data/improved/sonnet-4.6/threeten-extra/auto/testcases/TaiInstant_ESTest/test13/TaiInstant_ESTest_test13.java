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
public class TaiInstant_ESTest_test13 extends TaiInstant_ESTest_scaffolding {

    // nano-of-second values must be in [0, 999_999_999]; 1_000_000_000 equals one full second
    private static final int ONE_BILLION_NANOS = 1000000000;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        TaiInstant instant = TaiInstant.ofTaiSeconds(1000000000, 1000000000);

        // withNano rejects values >= 1_000_000_000 because that overflows into the next second
        try {
            instant.withNano(ONE_BILLION_NANOS);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.threeten.extra.scale.TaiInstant", e);
        }
    }
}
