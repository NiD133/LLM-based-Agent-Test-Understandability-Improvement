package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test02 extends Weeks_ESTest_scaffolding {

    /**
     * A {@code Weeks} instance must be equal to itself (reflexivity of equals),
     * and it must report the exact number of weeks it was created with.
     */
    @Test(timeout = 4000)
    public void weeksIsEqualToItselfAndReportsItsAmount() throws Throwable {
        Weeks nineHundredTwentyTwoWeeks = Weeks.of(922);

        boolean equalToItself = nineHundredTwentyTwoWeeks.equals(nineHundredTwentyTwoWeeks);

        assertTrue("a Weeks instance should be equal to itself", equalToItself);
        assertEquals("getAmount() should return the value passed to Weeks.of()",
                922, nineHundredTwentyTwoWeeks.getAmount());
    }
}
