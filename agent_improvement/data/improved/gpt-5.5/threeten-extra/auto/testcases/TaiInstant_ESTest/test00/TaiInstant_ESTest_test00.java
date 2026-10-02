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
public class TaiInstant_ESTest_test00 extends TaiInstant_ESTest_scaffolding {

    private static final long INPUT_TAI_SECONDS = -3113L;
    private static final long INPUT_NANO_ADJUSTMENT = -3113L;
    private static final int EXPECTED_NORMALIZED_NANO = 999996887;
    private static final long EXPECTED_NORMALIZED_SECONDS = -3114L;

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        TaiInstant firstInstant = TaiInstant.ofTaiSeconds(INPUT_TAI_SECONDS, INPUT_NANO_ADJUSTMENT);
        TaiInstant equivalentInstant = TaiInstant.ofTaiSeconds(INPUT_TAI_SECONDS, INPUT_NANO_ADJUSTMENT);

        boolean instantsAreEqual = firstInstant.equals(equivalentInstant);

        assertEquals(EXPECTED_NORMALIZED_NANO, equivalentInstant.getNano());
        assertTrue(instantsAreEqual);
        assertEquals(EXPECTED_NORMALIZED_SECONDS, equivalentInstant.getTaiSeconds());
    }
}
