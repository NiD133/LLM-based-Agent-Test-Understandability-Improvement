package org.threeten.extra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test15 extends Months_ESTest_scaffolding {

    /**
     * Verifies that the {@code Months.ONE} constant represents a non-zero
     * amount of exactly one month.
     */
    @Test(timeout = 4000)
    public void oneMonthIsNotZeroAndHasAmountOfOne() throws Throwable {
        Months oneMonth = Months.ONE;

        assertFalse("ONE should not be considered a zero amount", oneMonth.isZero());
        assertEquals("ONE should hold an amount of 1 month", 1, oneMonth.getAmount());
    }
}
