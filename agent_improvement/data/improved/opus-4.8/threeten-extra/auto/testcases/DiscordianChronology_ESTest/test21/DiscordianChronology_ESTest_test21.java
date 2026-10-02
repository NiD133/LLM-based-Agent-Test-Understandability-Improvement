package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.ZoneId;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test21 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link DiscordianChronology#dateNow(ZoneId)} rejects a null
     * zone. The implementation delegates to {@code java.util.Objects} for the
     * null check, so a {@link NullPointerException} originating from that class
     * is expected.
     */
    @Test(timeout = 4000)
    public void dateNowWithNullZoneThrowsNullPointerException() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        try {
            chronology.dateNow((ZoneId) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The null "zone" argument is rejected by java.util.Objects.
            verifyException("java.util.Objects", e);
        }
    }
}
