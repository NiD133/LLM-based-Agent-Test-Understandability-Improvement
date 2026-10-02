package org.threeten.extra.chrono;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.chrono.Era;
import java.time.chrono.JapaneseEra;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test17 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * dateYearDay(Era, ...) only accepts a DiscordianEra. Passing an era from
     * another chronology (here a JapaneseEra) must be rejected with a
     * ClassCastException stating the era must be DiscordianEra.YOLD.
     */
    @Test(timeout = 4000)
    public void dateYearDay_withNonDiscordianEra_throwsClassCastException() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        Era incompatibleEra = JapaneseEra.MEIJI;
        int yearOfEra = -2141534938;
        int dayOfYear = -2141534938;

        try {
            chronology.dateYearDay(incompatibleEra, yearOfEra, dayOfYear);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            // Message: "Era must be DiscordianEra.YOLD"
            verifyException("org.threeten.extra.chrono.DiscordianChronology", e);
        }
    }
}
