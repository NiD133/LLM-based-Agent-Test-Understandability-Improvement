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
public class UtcInstant_ESTest_test03 extends UtcInstant_ESTest_scaffolding {

    // UtcInstant.toString() uses a racy single-check cache; calling it twice
    // exercises both the cache-miss (first call) and cache-hit (second call) paths.
    @Test(timeout = 4000)
    public void test_toString_returnsISO8601String_andIsCachedOnSubsequentCall() throws Throwable {
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        utcInstant.toString(); // first call: populates the internal toString cache
        String formattedInstant = utcInstant.toString(); // second call: returns the cached value

        assertNotNull(formattedInstant);
        assertEquals("1970-01-01T00:00:03Z", formattedInstant);
    }
}
