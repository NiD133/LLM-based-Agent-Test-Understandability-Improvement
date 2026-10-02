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
     * Converting an {@link Instant} to a {@link UtcInstant} and back should
     * yield an Instant equal to the original, and the resulting UtcInstant
     * should sit on the expected Modified Julian Day.
     */
    @Test(timeout = 4000)
    public void toInstant_afterOf_roundTripsAndKeepsModifiedJulianDay() throws Throwable {
        // The mocked clock is fixed, so "now" corresponds to a known day.
        Instant originalInstant = MockInstant.now();

        UtcInstant utcInstant = UtcInstant.of(originalInstant);
        Instant roundTrippedInstant = utcInstant.toInstant();

        // Round-tripping through UtcInstant preserves the original Instant.
        assertTrue(roundTrippedInstant.equals((Object) originalInstant));
        // The fixed clock lands on Modified Julian Day 56702.
        assertEquals(56702L, utcInstant.getModifiedJulianDay());
    }
}
