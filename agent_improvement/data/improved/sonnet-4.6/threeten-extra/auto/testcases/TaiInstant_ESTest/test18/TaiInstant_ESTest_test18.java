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

    // TAI epoch is 1958-01-01T00:00:00(TAI); at TAI second 25 the TAI-UTC offset is 10 seconds,
    // so the UTC instant falls at second 15 of the day, giving getNanoOfDay() = 15s * 1e9 + 25ns.
    private static final long TAI_SECONDS = 25L;
    private static final long NANO_ADJUSTMENT = 25L;
    private static final long EXPECTED_NANO_OF_DAY = 15_000_000_025L;

    @Test(timeout = 4000)
    public void testToUtcInstant_nanoOfDayReflectsTaiToUtcOffset() throws Throwable {
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(TAI_SECONDS, NANO_ADJUSTMENT);
        UtcInstant utcInstant = taiInstant.toUtcInstant();
        assertEquals(EXPECTED_NANO_OF_DAY, utcInstant.getNanoOfDay());
    }
}
