package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test19 extends Days_ESTest_scaffolding {

    /**
     * The constant {@link Days#ONE} represents an amount of exactly one day,
     * so it holds an amount of 1 and is not considered negative.
     */
    @Test(timeout = 4000)
    public void oneDayIsNotNegativeAndHasAmountOfOne() throws Throwable {
        Days oneDay = Days.ONE;

        assertFalse("one day should not be negative", oneDay.isNegative());
        assertEquals("one day should report an amount of 1", 1, oneDay.getAmount());
    }
}
