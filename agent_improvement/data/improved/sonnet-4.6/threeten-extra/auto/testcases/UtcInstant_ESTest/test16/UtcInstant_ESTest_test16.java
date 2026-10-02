package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test16 extends UtcInstant_ESTest_scaffolding {

    // MJD and nano-of-day expected for the deterministic MockInstant.now() value used by EvoSuite
    private static final long EXPECTED_MJD = 56702L;
    private static final long EXPECTED_NANO_OF_DAY = 73281320000000L;

    @Test(timeout = 4000)
    public void test_minusZeroDuration_returnsSameInstant() throws Throwable {
        // Obtain a deterministic "now" instant via the EvoSuite mock clock
        Instant mockNow = MockInstant.now();
        UtcInstant utcNow = UtcInstant.of(mockNow);

        // durationUntil(self) must be zero; subtracting zero must yield an equal instant
        Duration zeroDuration = utcNow.durationUntil(utcNow);
        UtcInstant utcAfterSubtract = utcNow.minus(zeroDuration);

        assertTrue(utcAfterSubtract.equals((Object) utcNow));
        assertEquals(EXPECTED_MJD, utcNow.getModifiedJulianDay());
        assertEquals(EXPECTED_NANO_OF_DAY, utcAfterSubtract.getNanoOfDay());
    }
}
