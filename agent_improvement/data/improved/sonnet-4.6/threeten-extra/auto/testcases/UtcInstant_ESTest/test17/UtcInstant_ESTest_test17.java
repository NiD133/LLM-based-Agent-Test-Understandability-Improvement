package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test17 extends UtcInstant_ESTest_scaffolding {

    /**
     * Verifies that converting a mocked {@code Instant} (a fixed point in time used by EvoSuite)
     * to a {@code UtcInstant} produces the correct Modified Julian Day and nano-of-day values,
     * and that {@code hashCode()} completes without error.
     *
     * MockInstant.now() returns a deterministic, fixed instant (2014-02-08T20:21:21.320Z).
     * MJD 56702 corresponds to 2014-02-08.
     * Nano-of-day 73,281,320,000,000 equals 20 h 21 min 21 s 320 ms expressed in nanoseconds.
     */
    @Test(timeout = 4000)
    public void test_convertMockedInstantToUtcInstant_returnsExpectedMjdAndNanoOfDay() throws Throwable {
        // MockInstant.now() returns a fixed, deterministic Instant (2014-02-08T20:21:21.320Z)
        Instant mockedNow = MockInstant.now();
        UtcInstant utcInstant = UtcInstant.of(mockedNow);

        // hashCode() must not throw — exercises the hash mix of mjDay and nanoOfDay
        utcInstant.hashCode();

        // 73,281,320,000,000 ns = 20 h 21 min 21 s 320 ms since midnight UTC
        assertEquals(73281320000000L, utcInstant.getNanoOfDay());
        // MJD 56702 = calendar date 2014-02-08 (days since 1858-11-17)
        assertEquals(56702L, utcInstant.getModifiedJulianDay());
    }
}
