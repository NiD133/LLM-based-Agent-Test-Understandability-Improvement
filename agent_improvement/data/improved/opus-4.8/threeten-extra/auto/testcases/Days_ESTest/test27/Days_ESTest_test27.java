package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test27 extends Days_ESTest_scaffolding {

    /**
     * Calling hashCode() must not disturb the amount held by a Days instance.
     * A Days of 3 days should still report 3 days afterwards.
     */
    @Test(timeout = 4000)
    public void hashCodeDoesNotChangeAmount() throws Throwable {
        Days threeDays = Days.of(3);

        threeDays.hashCode();

        assertEquals(3, threeDays.getAmount());
    }
}
