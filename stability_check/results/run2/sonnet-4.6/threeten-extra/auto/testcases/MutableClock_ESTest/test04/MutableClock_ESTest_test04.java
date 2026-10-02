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
     * Two independently created MutableClock instances are not equal even when
     * they share the same initial instant and zone, because MutableClock equality
     * is based on shared-update identity (same InstantHolder), not clock value.
     */
    @Test(timeout = 4000)
    public void test04_twoSeparateEpochUTCClocksAreNotEqual() throws Throwable {
        MutableClock firstClock = MutableClock.epochUTC();
        MutableClock secondClock = MutableClock.epochUTC();

        boolean clocksAreEqual = secondClock.equals(firstClock);

        assertFalse(clocksAreEqual);
    }
}
