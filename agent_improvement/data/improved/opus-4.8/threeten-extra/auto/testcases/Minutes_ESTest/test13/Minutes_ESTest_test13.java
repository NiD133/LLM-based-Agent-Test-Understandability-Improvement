package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test13 extends Minutes_ESTest_scaffolding {

    private static final int MINUTES_PER_HOUR = 60;

    /**
     * A positive number of hours should produce a positive Minutes value whose
     * amount equals the hours multiplied by 60.
     */
    @Test(timeout = 4000)
    public void ofHours_convertsPositiveHoursToMinutesAndReportsPositive() throws Throwable {
        int hours = 53038;
        Minutes minutes = Minutes.ofHours(hours);

        assertTrue("A positive number of hours should be positive", minutes.isPositive());
        assertEquals("Amount should be hours converted to minutes",
                hours * MINUTES_PER_HOUR, minutes.getAmount());
    }
}
