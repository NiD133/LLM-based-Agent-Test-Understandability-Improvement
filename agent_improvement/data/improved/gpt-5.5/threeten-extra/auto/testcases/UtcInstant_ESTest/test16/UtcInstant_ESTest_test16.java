package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test16 extends UtcInstant_ESTest_scaffolding {

    private static final long EXPECTED_MODIFIED_JULIAN_DAY = 56702L;
    private static final long EXPECTED_NANO_OF_DAY = 73281320000000L;

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        Instant mockedNow = MockInstant.now();
        UtcInstant utcInstant = UtcInstant.of(mockedNow);

        Duration durationToSelf = utcInstant.durationUntil(utcInstant);
        UtcInstant resultAfterSubtractingDuration = utcInstant.minus(durationToSelf);

        assertTrue(resultAfterSubtractingDuration.equals((Object) utcInstant));
        assertEquals(EXPECTED_MODIFIED_JULIAN_DAY, utcInstant.getModifiedJulianDay());
        assertEquals(EXPECTED_NANO_OF_DAY, resultAfterSubtractingDuration.getNanoOfDay());
    }
}
