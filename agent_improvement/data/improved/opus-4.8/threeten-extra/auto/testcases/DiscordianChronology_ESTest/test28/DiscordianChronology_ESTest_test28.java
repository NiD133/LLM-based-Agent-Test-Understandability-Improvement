package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test28 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link DiscordianChronology#eraOf(int)} rejects an invalid era value.
     * The Discordian calendar defines only one era (YOLD, value 1), so passing -1
     * must cause {@code DiscordianEra.of} to throw a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void eraOf_withInvalidEraValue_throwsDateTimeException() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        try {
            chronology.eraOf(-1);
            fail("Expected a DateTimeException for invalid era value -1");
        } catch (DateTimeException e) {
            // The exception is raised while looking up the era in DiscordianEra.
            // Expected message: "Invalid era: -1"
            verifyException("org.threeten.extra.chrono.DiscordianEra", e);
        }
    }
}
