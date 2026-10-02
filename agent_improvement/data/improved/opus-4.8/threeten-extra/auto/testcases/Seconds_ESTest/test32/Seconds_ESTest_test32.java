package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test32 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that {@code toString()} renders an amount created from hours as the
     * equivalent number of seconds in ISO-8601 format ('PTnS').
     * 31 hours = 31 * 3600 = 111600 seconds, so the expected text is "PT111600S".
     */
    @Test(timeout = 4000)
    public void toString_rendersHoursAsTotalSecondsInIso8601Format() throws Throwable {
        Seconds thirtyOneHours = Seconds.ofHours(31);

        String formatted = thirtyOneHours.toString();

        assertEquals("PT111600S", formatted);
    }
}
