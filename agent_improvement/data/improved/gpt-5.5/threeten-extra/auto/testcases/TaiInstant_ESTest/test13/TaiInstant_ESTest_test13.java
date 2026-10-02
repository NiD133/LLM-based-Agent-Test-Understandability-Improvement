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

    private static final long TAI_SECONDS = 1000000000L;
    private static final long NANO_ADJUSTMENT = 1000000000L;
    private static final int FIRST_INVALID_NANO_OF_SECOND = 1000000000;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        TaiInstant instantAtBoundary = TaiInstant.ofTaiSeconds(TAI_SECONDS, NANO_ADJUSTMENT);

        try {
            instantAtBoundary.withNano(FIRST_INVALID_NANO_OF_SECOND);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.threeten.extra.scale.TaiInstant", e);
        }
    }
}
