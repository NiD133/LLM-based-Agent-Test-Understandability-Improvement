package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test14 extends Weeks_ESTest_scaffolding {

    /**
     * The constant {@link Weeks#ONE} holds an amount of one week, which is a
     * strictly positive value. Verify that its amount is 1 and that
     * {@link Weeks#isPositive()} therefore reports {@code true}.
     */
    @Test(timeout = 4000)
    public void oneWeekIsPositive() throws Throwable {
        Weeks oneWeek = Weeks.ONE;

        boolean positive = oneWeek.isPositive();

        assertEquals("Weeks.ONE should hold an amount of 1", 1, oneWeek.getAmount());
        assertTrue("an amount of one week is positive", positive);
    }
}
