package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test23 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that the Discordian chronology's textual representation is "Discordian",
     * which is derived from its chronology ID.
     */
    @Test(timeout = 4000)
    public void toStringReturnsChronologyId() throws Throwable {
        DiscordianChronology chronology = DiscordianChronology.INSTANCE;

        String textualRepresentation = chronology.toString();

        assertEquals("Discordian", textualRepresentation);
    }
}
