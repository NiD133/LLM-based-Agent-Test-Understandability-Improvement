package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import java.time.chrono.HijrahEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test25 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that passing a non-Discordian era (HijrahEra) to
     * {@link DiscordianChronology#date(Era, int, int, int)} throws a
     * {@link ClassCastException}, because the method requires a {@link DiscordianEra}.
     */
    @Test(timeout = 4000)
    public void test_dateWithNonDiscordianEra_throwsClassCastException() throws Throwable {
        DiscordianChronology chronology = DiscordianChronology.INSTANCE;

        // HijrahEra is an incompatible Era type; only DiscordianEra.YOLD is accepted
        Era incompatibleEra = HijrahEra.AH;

        try {
            chronology.date(incompatibleEra, 764, 764, 764);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            // Expected message: "Era must be DiscordianEra.YOLD"
            verifyException("org.threeten.extra.chrono.DiscordianChronology", e);
        }
    }
}
