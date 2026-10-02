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
     * Two clocks created by separate calls to {@code epochUTC()} are not equal,
     * because each owns its own (non-shared) mutable instant, even though both
     * start at the same instant and time-zone.
     */
    @Test(timeout = 4000)
    public void separatelyCreatedClocksAreNotEqual() throws Throwable {
        MutableClock firstClock = MutableClock.epochUTC();
        MutableClock secondClock = MutableClock.epochUTC();

        boolean clocksAreEqual = secondClock.equals(firstClock);

        assertFalse(clocksAreEqual);
    }
}
