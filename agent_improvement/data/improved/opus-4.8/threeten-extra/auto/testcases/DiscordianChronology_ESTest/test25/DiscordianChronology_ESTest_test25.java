package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.chrono.Era;
import java.time.chrono.HijrahEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test25 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * The Discordian chronology only accepts its own {@link DiscordianEra}. Passing an era from a
     * different calendar system (here {@link HijrahEra#AH}) to {@code date(Era, year, month, day)}
     * must fail fast with a {@link ClassCastException}.
     */
    @Test(timeout = 4000)
    public void dateWithForeignEraThrowsClassCastException() throws Throwable {
        DiscordianChronology discordianChronology = DiscordianChronology.INSTANCE;
        Era foreignEra = HijrahEra.AH;

        try {
            discordianChronology.date(foreignEra, 764, 764, 764);
            fail("Expected ClassCastException because the era is not a DiscordianEra.YOLD");
        } catch (ClassCastException e) {
            // Message thrown by DiscordianChronology: "Era must be DiscordianEra.YOLD"
            verifyException("org.threeten.extra.chrono.DiscordianChronology", e);
        }
    }
}
