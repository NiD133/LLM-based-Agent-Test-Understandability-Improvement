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
     * A {@code Weeks} instance should be equal to itself (reflexivity of equals),
     * and should retain the amount it was created with.
     */
    @Test(timeout = 4000)
    public void equalsIsReflexiveAndAmountIsPreserved() throws Throwable {
        Weeks nineHundredTwentyTwoWeeks = Weeks.of(922);

        boolean equalToItself = nineHundredTwentyTwoWeeks.equals(nineHundredTwentyTwoWeeks);

        assertTrue("A Weeks instance must be equal to itself", equalToItself);
        assertEquals("The amount should match the value passed to Weeks.of",
                922, nineHundredTwentyTwoWeeks.getAmount());
    }
}
