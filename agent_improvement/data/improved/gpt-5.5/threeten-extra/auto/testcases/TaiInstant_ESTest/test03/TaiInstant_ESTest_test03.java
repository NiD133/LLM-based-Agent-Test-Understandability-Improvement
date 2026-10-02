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
public class TaiInstant_ESTest_test03 extends TaiInstant_ESTest_scaffolding {

    private static final long ORIGINAL_TAI_SECONDS = 3600000000000L;
    private static final long NEGATIVE_NANO_ADJUSTMENT = -1194L;
    private static final long NORMALIZED_TAI_SECONDS = 3599999999999L;
    private static final int NORMALIZED_NANO_OF_SECOND = 999998806;

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        TaiInstant normalizedInstant = TaiInstant.ofTaiSeconds(ORIGINAL_TAI_SECONDS, NEGATIVE_NANO_ADJUSTMENT);

        boolean isEqualToItself = normalizedInstant.equals(normalizedInstant);

        assertTrue(isEqualToItself);
        assertEquals(NORMALIZED_TAI_SECONDS, normalizedInstant.getTaiSeconds());
        assertEquals(NORMALIZED_NANO_OF_SECOND, normalizedInstant.getNano());
    }
}
