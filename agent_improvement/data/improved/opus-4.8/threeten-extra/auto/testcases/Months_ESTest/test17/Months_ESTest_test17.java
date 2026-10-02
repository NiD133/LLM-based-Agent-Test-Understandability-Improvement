package org.threeten.extra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test17 extends Months_ESTest_scaffolding {

    /**
     * The constant {@link Months#ONE} represents a positive amount of one month,
     * so it is not negative and its amount is 1.
     */
    @Test(timeout = 4000)
    public void oneMonthIsNotNegativeAndHasAmountOne() throws Throwable {
        Months oneMonth = Months.ONE;

        assertFalse("one month should not be negative", oneMonth.isNegative());
        assertEquals("one month should report an amount of 1", 1, oneMonth.getAmount());
    }
}
