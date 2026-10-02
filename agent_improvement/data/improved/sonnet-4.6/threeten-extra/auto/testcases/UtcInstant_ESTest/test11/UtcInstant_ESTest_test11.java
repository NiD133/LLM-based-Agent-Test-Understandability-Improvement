package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test11 extends UtcInstant_ESTest_scaffolding {

    // Negative TAI seconds/nanoseconds represent a time well before the TAI epoch (1958-01-01)
    private static final long TAI_SECONDS = -24L;
    private static final long TAI_NANOSECONDS = -24L;

    // The replacement MJD passed to withModifiedJulianDay(); equal to the TAI input for clarity
    private static final long REPLACEMENT_MJD = -24L;

    // Expected nano-of-day on the original UTC instant after TAI->UTC conversion
    private static final long EXPECTED_NANO_OF_DAY = 86365999999976L;

    /**
     * Verifies that:
     *  1. A negative TAI instant converts to a UTC instant whose nano-of-day is EXPECTED_NANO_OF_DAY.
     *  2. Replacing only the MJD via withModifiedJulianDay() yields an earlier instant.
     *  3. isAfter() correctly identifies the original as chronologically later.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Convert a negative TAI instant to its UTC representation
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(TAI_SECONDS, TAI_NANOSECONDS);
        UtcInstant originalUtc = taiInstant.toUtcInstant();

        // Replace the MJD while keeping the nano-of-day unchanged
        UtcInstant earlierMjdUtc = originalUtc.withModifiedJulianDay(REPLACEMENT_MJD);

        // originalUtc has a later MJD than REPLACEMENT_MJD, so it must be after earlierMjdUtc
        boolean originalIsAfterEarlier = originalUtc.isAfter(earlierMjdUtc);

        assertEquals(REPLACEMENT_MJD, earlierMjdUtc.getModifiedJulianDay());
        assertTrue(originalIsAfterEarlier);
        assertEquals(EXPECTED_NANO_OF_DAY, originalUtc.getNanoOfDay());
    }
}
