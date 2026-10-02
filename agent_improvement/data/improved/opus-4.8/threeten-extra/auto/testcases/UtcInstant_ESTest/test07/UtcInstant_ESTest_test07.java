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
public class UtcInstant_ESTest_test07 extends UtcInstant_ESTest_scaffolding {

    /**
     * A UtcInstant should be equal to itself (the reflexive property of equals),
     * and converting an Instant 3 seconds after the Unix epoch should map to
     * Modified Julian Day 40587 (1970-01-01) with a nano-of-day of 3 seconds.
     */
    @Test(timeout = 4000)
    public void equalsIsReflexiveAndConversionKeepsEpochFields() throws Throwable {
        // Instant 3 seconds after the Unix epoch (1970-01-01T00:00:03Z).
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        boolean equalToItself = utcInstant.equals(utcInstant);

        assertTrue("a UtcInstant must equal itself", equalToItself);
        // Modified Julian Day 40587 corresponds to the Unix epoch date 1970-01-01.
        assertEquals(40587L, utcInstant.getModifiedJulianDay());
        // 3 seconds expressed in nanoseconds.
        assertEquals(3000000000L, utcInstant.getNanoOfDay());
    }
}
