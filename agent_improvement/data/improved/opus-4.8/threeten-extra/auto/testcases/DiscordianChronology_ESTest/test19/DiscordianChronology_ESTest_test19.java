package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.Era;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test19 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that the Discordian chronology exposes at least one era.
     * The chronology is obtained from a freshly created "today" Discordian date.
     */
    @Test(timeout = 4000)
    public void erasShouldNotBeEmpty() throws Throwable {
        DiscordianDate today = DiscordianDate.now();
        DiscordianChronology chronology = today.getChronology();

        List<Era> eras = chronology.eras();

        assertFalse("Discordian chronology must define at least one era", eras.isEmpty());
    }
}
