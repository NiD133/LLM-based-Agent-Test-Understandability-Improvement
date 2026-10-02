package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test08 extends Weeks_ESTest_scaffolding {

    /**
     * The absolute value of zero weeks is still zero weeks.
     */
    @Test(timeout = 4000)
    public void absOfZeroWeeksIsZero() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;

        Weeks absoluteValue = zeroWeeks.abs();

        assertEquals(0, absoluteValue.getAmount());
    }
}
