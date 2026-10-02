package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test04 extends MutableClock_ESTest_scaffolding {

    /**
     * Two independently created clocks are not equal, even when they start at
     * the same instant and zone. MutableClock#equals only treats clocks as
     * equal when they share the same underlying (identity-based) instant holder,
     * which two separate epochUTC() instances never do.
     */
    @Test(timeout = 4000)
    public void twoSeparatelyCreatedClocksAreNotEqual() throws Throwable {
        MutableClock firstClock = MutableClock.epochUTC();
        MutableClock secondClock = MutableClock.epochUTC();

        boolean clocksAreEqual = secondClock.equals(firstClock);

        assertFalse(clocksAreEqual);
    }
}
