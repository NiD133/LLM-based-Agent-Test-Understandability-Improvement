package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test27 extends Months_ESTest_scaffolding {

    /**
     * Subtracting an amount from itself yields zero months, which is not positive,
     * while the original "one month" value is left unchanged and remains positive.
     */
    @Test(timeout = 4000)
    public void subtractingOneMonthFromItselfYieldsNonPositiveZero() throws Throwable {
        Months oneMonth = Months.ONE;

        Months difference = oneMonth.minus((TemporalAmount) oneMonth);

        // The result is zero months and therefore not positive.
        assertEquals(0, difference.getAmount());
        assertFalse(difference.isPositive());

        // The original instance is immutable: still one, still positive.
        assertEquals(1, oneMonth.getAmount());
        assertTrue(oneMonth.isPositive());
    }
}
