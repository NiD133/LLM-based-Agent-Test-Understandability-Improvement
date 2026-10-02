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
public class UtcInstant_ESTest_test04 extends UtcInstant_ESTest_scaffolding {

    /**
     * Two UtcInstants that fall on the same Modified Julian Day but have a
     * different nano-of-day must not be considered equal, since equality is
     * based on both the day and the nano-of-day.
     */
    @Test(timeout = 4000)
    public void withNanoOfDay_changesNanoButKeepsDay_makesInstantsUnequal() throws Throwable {
        // The mocked clock is fixed, so this instant falls on MJD 56702.
        long expectedModifiedJulianDay = 56702L;
        Instant fixedInstant = MockInstant.now();
        UtcInstant originalInstant = UtcInstant.of(fixedInstant);

        // Derive a copy on the same day but with a different nano-of-day.
        UtcInstant instantWithDifferentNanos = originalInstant.withNanoOfDay(1136L);

        // The day is preserved by withNanoOfDay, but the nano-of-day differs...
        assertEquals(expectedModifiedJulianDay, originalInstant.getModifiedJulianDay());
        assertEquals(expectedModifiedJulianDay, instantWithDifferentNanos.getModifiedJulianDay());

        // ...so the two instants are not equal, in either direction.
        assertFalse(instantWithDifferentNanos.equals(originalInstant));
        assertFalse(originalInstant.equals((Object) instantWithDifferentNanos));
    }
}
