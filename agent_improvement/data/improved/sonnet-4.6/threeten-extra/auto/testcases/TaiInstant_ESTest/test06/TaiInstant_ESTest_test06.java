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
public class TaiInstant_ESTest_test06 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that isBefore returns false when an instant is compared to itself,
     * and that the TAI seconds and nano-of-second fields retain their original values.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Create a TAI instant at second 20 with a nano-of-second adjustment of 20
        TaiInstant instant = TaiInstant.ofTaiSeconds(20, 20);

        // An instant cannot be before itself
        boolean isBeforeItself = instant.isBefore(instant);

        assertEquals("TAI seconds should be 20", 20L, instant.getTaiSeconds());
        assertFalse("An instant should not be before itself", isBeforeItself);
        assertEquals("Nano-of-second should be 20", 20, instant.getNano());
    }
}
