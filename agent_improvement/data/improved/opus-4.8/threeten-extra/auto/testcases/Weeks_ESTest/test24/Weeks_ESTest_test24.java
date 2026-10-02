package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test24 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that negating and then taking the absolute value of {@code Weeks.ONE}
     * returns to the original positive amount.
     * <p>
     * Negating {@code ONE} (1 week) yields -1 weeks, and taking the absolute value
     * of -1 weeks restores 1 week. Since {@link Weeks#abs()} returns a positive
     * amount and {@link Weeks#of(int)} reuses the {@link Weeks#ONE} singleton for a
     * value of 1, the result is the very same instance as {@code Weeks.ONE}.
     */
    @Test(timeout = 4000)
    public void absOfNegatedOneReturnsOneSingleton() throws Throwable {
        Weeks oneWeek = Weeks.ONE;

        Weeks negativeOneWeek = oneWeek.negated();
        Weeks absoluteValue = negativeOneWeek.abs();

        assertEquals("negating one week should give -1 weeks", -1, negativeOneWeek.getAmount());
        assertSame("abs of -1 week should yield the Weeks.ONE singleton", absoluteValue, oneWeek);
    }
}
