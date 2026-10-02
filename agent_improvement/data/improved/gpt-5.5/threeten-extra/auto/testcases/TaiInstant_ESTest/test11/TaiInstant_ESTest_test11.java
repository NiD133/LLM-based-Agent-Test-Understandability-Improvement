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
public class TaiInstant_ESTest_test11 extends TaiInstant_ESTest_scaffolding {

    private static final long INPUT_TAI_SECONDS = -10L;
    private static final long INPUT_NANO_ADJUSTMENT = -10L;
    private static final long NORMALIZED_TAI_SECONDS = -11L;
    private static final int NORMALIZED_NANO_OF_SECOND = 999999990;

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        TaiInstant normalizedNegativeAdjustment =
                TaiInstant.ofTaiSeconds(INPUT_TAI_SECONDS, INPUT_NANO_ADJUSTMENT);
        Duration zeroDuration = Duration.ZERO;

        TaiInstant result = normalizedNegativeAdjustment.plus(zeroDuration);

        assertSame(result, normalizedNegativeAdjustment);
        assertEquals(NORMALIZED_TAI_SECONDS, result.getTaiSeconds());
        assertEquals(NORMALIZED_NANO_OF_SECOND, result.getNano());
    }
}
