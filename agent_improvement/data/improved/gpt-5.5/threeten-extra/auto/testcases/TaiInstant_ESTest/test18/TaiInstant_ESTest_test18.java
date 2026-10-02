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
public class TaiInstant_ESTest_test18 extends TaiInstant_ESTest_scaffolding {

    private static final long TAI_SECONDS = 25L;
    private static final long NANO_ADJUSTMENT = 25L;
    private static final long EXPECTED_UTC_NANO_OF_DAY = 15000000025L;

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(TAI_SECONDS, NANO_ADJUSTMENT);

        UtcInstant utcInstant = taiInstant.toUtcInstant();

        assertEquals(EXPECTED_UTC_NANO_OF_DAY, utcInstant.getNanoOfDay());
    }
}
