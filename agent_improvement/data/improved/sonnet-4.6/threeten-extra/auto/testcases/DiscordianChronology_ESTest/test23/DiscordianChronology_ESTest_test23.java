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
     * Verifies that the Discordian chronology singleton's toString() returns its calendar ID "Discordian".
     * The constructor is called (despite being deprecated) to match the original test behaviour,
     * then INSTANCE is accessed via the created object to reach the singleton.
     */
    @Test(timeout = 4000)
    public void test23() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        String chronologyName = chronology.INSTANCE.toString();
        assertEquals("Discordian", chronologyName);
    }
}
