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
     * Converting an Instant to a UtcInstant and back should round-trip to an
     * equal Instant, and the resulting UtcInstant should report the expected
     * Modified Julian Day for the mocked "now" time.
     */
    @Test(timeout = 4000)
    public void toInstantRoundTripsAndExposesModifiedJulianDay() throws Throwable {
        Instant originalInstant = MockInstant.now();

        UtcInstant utcInstant = UtcInstant.of(originalInstant);
        Instant roundTrippedInstant = utcInstant.toInstant();

        assertEquals(originalInstant, roundTrippedInstant);
        assertEquals(56702L, utcInstant.getModifiedJulianDay());
    }
}
