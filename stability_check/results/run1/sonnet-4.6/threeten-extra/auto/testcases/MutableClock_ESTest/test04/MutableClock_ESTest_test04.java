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
     * they share the same instant and zone. MutableClock.equals() requires the
     * two clocks to share the same internal InstantHolder instance (identity
     * equality), not just the same instant value. Clocks created via separate
     * epochUTC() calls each own a distinct InstantHolder, so they are unequal.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        MutableClock firstClock = MutableClock.epochUTC();
        MutableClock secondClock = MutableClock.epochUTC();

        boolean areEqual = secondClock.equals(firstClock);

        assertFalse(areEqual);
    }
}
