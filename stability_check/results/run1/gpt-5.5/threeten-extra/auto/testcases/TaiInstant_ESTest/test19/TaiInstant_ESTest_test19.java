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

    private static final long TAI_SECONDS = 20L;
    private static final long NANO_ADJUSTMENT = 20L;
    private static final int EXPECTED_NANO_OF_SECOND = 20;

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(TAI_SECONDS, NANO_ADJUSTMENT);

        taiInstant.toInstant();

        assertEquals(TAI_SECONDS, taiInstant.getTaiSeconds());
        assertEquals(EXPECTED_NANO_OF_SECOND, taiInstant.getNano());
    }
}
