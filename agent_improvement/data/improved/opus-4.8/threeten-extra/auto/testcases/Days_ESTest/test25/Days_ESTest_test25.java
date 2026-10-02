package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test25 extends Days_ESTest_scaffolding {

    /**
     * Verifies that {@link Days#of(int)} stores the supplied value and that
     * {@link Days#getAmount()} returns it unchanged.
     */
    @Test(timeout = 4000)
    public void of_oneDay_getAmountReturnsOne() throws Throwable {
        Days oneDay = Days.of(1);

        assertEquals(1, oneDay.getAmount());
    }
}
