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
public class UtcInstant_ESTest_test05 extends UtcInstant_ESTest_scaffolding {

    /**
     * A UtcInstant built from the current (mocked) Instant should expose the
     * expected modified-Julian-day and nano-of-day, and must not be considered
     * equal to a UtcInstant derived from a far-future TAI instant.
     */
    @Test(timeout = 4000)
    public void utcInstantFromNow_hasExpectedFields_andDiffersFromTaiBasedInstant() throws Throwable {
        // UtcInstant created from the current mocked Instant.
        Instant now = MockInstant.now();
        UtcInstant utcInstantFromNow = UtcInstant.of(now);

        // UtcInstant created from an unrelated, far-future TAI instant.
        TaiInstant farFutureTaiInstant = TaiInstant.ofTaiSeconds(36791000000652L, 36791000000652L);
        UtcInstant utcInstantFromTai = farFutureTaiInstant.toUtcInstant();

        // The two instants represent different points in time, so they are not equal.
        assertFalse(utcInstantFromNow.equals(utcInstantFromTai));

        // Verify the field values of the instant built from "now".
        assertEquals(56702L, utcInstantFromNow.getModifiedJulianDay());
        assertEquals(73281320000000L, utcInstantFromNow.getNanoOfDay());
    }
}
