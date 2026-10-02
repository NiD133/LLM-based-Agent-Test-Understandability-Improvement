package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test15 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Discordian leap years follow ISO rules offset by 1166: a proleptic year is
     * leap when (year - 1166) is divisible by 4, except centuries not divisible by 400.
     * For year 1266, the offset is 100, which is divisible by 100 but not 400, so it is
     * not a leap year.
     */
    @Test(timeout = 4000)
    public void isLeapYear_returnsFalseForNonLeapCenturyYear() throws Throwable {
        DiscordianChronology discordianChronology = new DiscordianChronology();

        boolean isLeapYear = discordianChronology.isLeapYear(1266L);

        assertFalse(isLeapYear);
    }
}
