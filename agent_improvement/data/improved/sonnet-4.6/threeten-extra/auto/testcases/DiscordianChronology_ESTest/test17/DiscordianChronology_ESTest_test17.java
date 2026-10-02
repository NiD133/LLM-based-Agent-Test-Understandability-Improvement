package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import java.time.chrono.JapaneseEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test17 extends DiscordianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17_dateYearDay_withNonDiscordianEra_throwsClassCastException() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();
        // JapaneseEra is not a DiscordianEra, so passing it should fail era validation
        Era nonDiscordianEra = JapaneseEra.MEIJI;
        int anyYearOfEra = -2141534938;
        int anyDayOfYear = -2141534938;
        try {
            chronology.dateYearDay(nonDiscordianEra, anyYearOfEra, anyDayOfYear);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            //
            // Era must be DiscordianEra.YOLD
            //
            verifyException("org.threeten.extra.chrono.DiscordianChronology", e);
        }
    }
}
