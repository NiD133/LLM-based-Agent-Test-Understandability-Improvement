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
public class TaiInstant_ESTest_test14 extends TaiInstant_ESTest_scaffolding {

    private static final int NEGATIVE_TAI_SECONDS = -377;
    private static final int NEGATIVE_NANO_ADJUSTMENT = -377;
    private static final int INVALID_NANO_OF_SECOND = -377;

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        TaiInstant instant = TaiInstant.ofTaiSeconds(NEGATIVE_TAI_SECONDS, NEGATIVE_NANO_ADJUSTMENT);

        try {
            instant.withNano(INVALID_NANO_OF_SECOND);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.threeten.extra.scale.TaiInstant", e);
        }
    }
}
