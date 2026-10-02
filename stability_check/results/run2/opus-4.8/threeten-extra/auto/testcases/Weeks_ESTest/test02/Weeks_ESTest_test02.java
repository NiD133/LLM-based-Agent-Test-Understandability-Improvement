package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test02 extends Weeks_ESTest_scaffolding {

    /**
     * A Weeks instance should be equal to itself (reflexivity of equals),
     * and should retain the exact amount it was created with.
     */
    @Test(timeout = 4000)
    public void equalsIsReflexiveAndAmountIsPreserved() throws Throwable {
        Weeks nineHundredTwentyTwoWeeks = Weeks.of(922);

        assertTrue("A Weeks instance must equal itself",
                nineHundredTwentyTwoWeeks.equals(nineHundredTwentyTwoWeeks));
        assertEquals("The amount must match the value passed to Weeks.of",
                922, nineHundredTwentyTwoWeeks.getAmount());
    }
}
