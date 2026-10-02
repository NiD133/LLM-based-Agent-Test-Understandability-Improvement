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
public class UtcInstant_ESTest_test15 extends UtcInstant_ESTest_scaffolding {

    /**
     * Verifies that converting an Instant to UtcInstant and back produces an equal Instant,
     * and that the resulting UtcInstant falls on the expected Modified Julian Day (MJD 56702).
     * MJD 56702 corresponds to the deterministic "now" returned by MockInstant in EvoSuite.
     */
    @Test(timeout = 4000)
    public void testRoundTripConversionFromInstantPreservesEqualityAndCorrectMJD() throws Throwable {
        // Obtain a deterministic mocked "current" instant
        Instant originalInstant = MockInstant.now();

        // Convert the standard Instant to a UTC-aware UtcInstant
        UtcInstant utcInstant = UtcInstant.of(originalInstant);

        // Convert back to a standard Instant — should round-trip to the same value
        Instant recoveredInstant = utcInstant.toInstant();
        assertTrue("Round-trip conversion should produce an equal Instant",
                recoveredInstant.equals((Object) originalInstant));

        // The mocked instant corresponds to Modified Julian Day 56702
        assertEquals("UtcInstant should fall on MJD 56702",
                56702L, utcInstant.getModifiedJulianDay());
    }
}
