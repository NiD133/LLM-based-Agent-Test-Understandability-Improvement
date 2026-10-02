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
public class TaiInstant_ESTest_test19 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that a TaiInstant created from 20 seconds and 20 nanoseconds
     * retains those exact field values, and that converting it to an Instant
     * completes without altering the original TaiInstant.
     */
    @Test(timeout = 4000)
    public void toInstantPreservesTaiSecondsAndNano() throws Throwable {
        long expectedTaiSeconds = 20L;
        int expectedNano = 20;

        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(expectedTaiSeconds, expectedNano);
        taiInstant.toInstant();

        assertEquals(expectedTaiSeconds, taiInstant.getTaiSeconds());
        assertEquals(expectedNano, taiInstant.getNano());
    }
}
