package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test16 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * A date built from a proleptic year, month and day in the Discordian
     * calendar should report YOLD as its era, since YOLD is the only
     * Discordian era.
     */
    @Test(timeout = 4000)
    public void dateFromYearMonthDay_hasYoldEra() throws Throwable {
        DiscordianChronology chronology = new DiscordianChronology();

        int prolepticYear = 4;
        int month = 4;
        int dayOfMonth = 4;
        DiscordianDate date = chronology.date(prolepticYear, month, dayOfMonth);

        assertEquals(DiscordianEra.YOLD, date.getEra());
    }
}
