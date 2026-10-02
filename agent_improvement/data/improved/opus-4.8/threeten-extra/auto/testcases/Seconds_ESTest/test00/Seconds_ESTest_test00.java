package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test00 extends Seconds_ESTest_scaffolding {

    /**
     * Two {@code Seconds} amounts built from the same number of hours should be
     * equal, and each should hold the equivalent number of seconds (4 hours =
     * 4 * 3600 = 14400 seconds).
     */
    @Test(timeout = 4000)
    public void fourHours_equalsAnotherFourHours_andHoldsExpectedSeconds() throws Throwable {
        Seconds fourHours = Seconds.ofHours(4);
        Seconds sameFourHours = Seconds.ofHours(4);

        assertTrue(fourHours.equals(sameFourHours));
        assertEquals(14400, sameFourHours.getAmount());
    }
}
