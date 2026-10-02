package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test06 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_equalsReturnsTrueWhenComparedToItself() throws Throwable {
        // MutableClock.equals() uses reference identity for the instant holder,
        // so a clock must be equal to itself.
        MutableClock clock = MutableClock.epochUTC();
        assertTrue(clock.equals(clock));
    }
}
