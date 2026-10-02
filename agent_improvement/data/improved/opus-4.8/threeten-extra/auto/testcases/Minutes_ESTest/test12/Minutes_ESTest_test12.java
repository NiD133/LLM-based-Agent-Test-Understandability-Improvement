package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test12 extends Minutes_ESTest_scaffolding {

    /**
     * Adding minutes to a {@code Minutes} amount yields a new, unequal instance.
     *
     * <p>327 hours equals 327 * 60 = 19620 minutes; adding 327 more minutes
     * gives 19947 minutes. The two amounts differ, so they must not be equal
     * to each other (in either direction).
     */
    @Test(timeout = 4000)
    public void plusMinutesProducesDifferentUnequalAmount() throws Throwable {
        Minutes baseAmount = Minutes.ofHours(327);
        Minutes increasedAmount = baseAmount.plus(327);

        assertEquals(19947, increasedAmount.getAmount());
        assertFalse(increasedAmount.equals(baseAmount));
        assertFalse(baseAmount.equals(increasedAmount));
    }
}
