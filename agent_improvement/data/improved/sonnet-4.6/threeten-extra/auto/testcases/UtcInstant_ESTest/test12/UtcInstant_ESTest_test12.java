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
public class UtcInstant_ESTest_test12 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12_utcInstantFromEpochSecond3_hasExpectedDayAndNanoProperties() throws Throwable {
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        boolean isAfterItself = utcInstant.isAfter(utcInstant);

        // 3 seconds past midnight = 3 * 1_000_000_000 nanoseconds
        assertEquals(3_000_000_000L, utcInstant.getNanoOfDay());
        assertFalse(isAfterItself);
        // MJD 40587 corresponds to 1970-01-01 (Unix epoch day)
        assertEquals(40587L, utcInstant.getModifiedJulianDay());
    }
}
