package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test13 extends DiscordianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        DiscordianDate currentDiscordianDate = DiscordianDate.now();
        DiscordianEra currentEra = currentDiscordianDate.getEra();

        int yearOfEra = chronology.INSTANCE.prolepticYear(currentEra, 47);

        assertEquals(47, yearOfEra);
    }
}
