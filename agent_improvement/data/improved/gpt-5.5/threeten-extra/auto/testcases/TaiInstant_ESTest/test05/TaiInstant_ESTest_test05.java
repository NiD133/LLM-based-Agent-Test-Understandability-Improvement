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
public class TaiInstant_ESTest_test05 extends TaiInstant_ESTest_scaffolding {

    private static final long ORIGINAL_SECONDS = -5L;
    private static final long NEGATIVE_NANO_ADJUSTMENT = -5L;
    private static final int NORMALIZED_NANO = 999999995;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        TaiInstant normalizedInstant = TaiInstant.ofTaiSeconds(ORIGINAL_SECONDS, NEGATIVE_NANO_ADJUSTMENT);
        TaiInstant sameNanoAtOriginalSecond = normalizedInstant.withTaiSeconds(ORIGINAL_SECONDS);

        boolean normalizedInstantIsBeforeAdjustedInstant = normalizedInstant.isBefore(sameNanoAtOriginalSecond);

        assertEquals(NORMALIZED_NANO, sameNanoAtOriginalSecond.getNano());
        assertEquals(ORIGINAL_SECONDS, sameNanoAtOriginalSecond.getTaiSeconds());
        assertEquals(NORMALIZED_NANO, normalizedInstant.getNano());
        assertTrue(normalizedInstantIsBeforeAdjustedInstant);
    }
}
