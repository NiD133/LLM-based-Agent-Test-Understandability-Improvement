package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test25 extends Seconds_ESTest_scaffolding {

    /**
     * Converting zero hours to seconds should yield an amount of zero seconds.
     */
    @Test(timeout = 4000)
    public void ofHoursWithZeroYieldsZeroSeconds() throws Throwable {
        Seconds zeroHoursInSeconds = Seconds.ofHours(0);

        assertEquals(0, zeroHoursInSeconds.getAmount());
    }
}
