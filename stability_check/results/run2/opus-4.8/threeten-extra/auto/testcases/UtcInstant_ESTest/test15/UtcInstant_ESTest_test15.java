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
     * yield an equal {@link Instant}, and the resulting UtcInstant should carry
     * the Modified Julian Day matching the mocked "now" value.
     */
    @Test(timeout = 4000)
    public void toInstantRoundTripsAndExposesModifiedJulianDay() throws Throwable {
        // The mocked clock returns a fixed instant whose date is Modified Julian Day 56702.
        Instant originalInstant = MockInstant.now();

        UtcInstant utcInstant = UtcInstant.of(originalInstant);
        Instant roundTrippedInstant = utcInstant.toInstant();

        assertTrue(roundTrippedInstant.equals((Object) originalInstant));
        assertEquals(56702L, utcInstant.getModifiedJulianDay());
    }
}
