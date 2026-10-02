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
     * Two independently created MutableClock instances are never equal,
     * even when both start at the epoch in UTC. Equality requires shared
     * internal state (via withZone), not merely the same instant and zone.
     */
    @Test(timeout = 4000)
    public void test_twoIndependentEpochUTCClocksAreNotEqual() throws Throwable {
        MutableClock firstClock = MutableClock.epochUTC();
        MutableClock secondClock = MutableClock.epochUTC();

        boolean areEqual = secondClock.equals(firstClock);

        assertFalse(areEqual);
    }
}
