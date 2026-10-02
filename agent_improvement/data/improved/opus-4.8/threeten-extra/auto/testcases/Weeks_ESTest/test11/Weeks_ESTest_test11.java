package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test11 extends Weeks_ESTest_scaffolding {

    /**
     * Multiplying a Weeks amount by a scalar of 1 leaves the amount unchanged.
     */
    @Test(timeout = 4000)
    public void multiplyingByOneKeepsSameAmount() throws Throwable {
        Weeks oneWeek = Weeks.of(1);

        Weeks result = oneWeek.multipliedBy(1);

        assertEquals(1, result.getAmount());
    }
}
