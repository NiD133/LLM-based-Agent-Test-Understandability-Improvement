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
public class UtcInstant_ESTest_test03 extends UtcInstant_ESTest_scaffolding {

    /**
     * Verifies that a UtcInstant created from an Instant three seconds after the
     * epoch renders as the expected ISO-8601 UTC string.
     */
    @Test(timeout = 4000)
    public void toString_threeSecondsAfterEpoch_returnsIsoUtcString() throws Throwable {
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        // Call once before asserting to mirror the original behaviour (toString has no side effects).
        utcInstant.toString();
        String formatted = utcInstant.toString();

        assertNotNull(formatted);
        assertEquals("1970-01-01T00:00:03Z", formatted);
    }
}
