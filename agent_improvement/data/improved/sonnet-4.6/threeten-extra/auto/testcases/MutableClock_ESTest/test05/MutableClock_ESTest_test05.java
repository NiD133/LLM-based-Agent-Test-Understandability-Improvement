package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test05 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_equals_returnsFalse_whenComparedToNonMutableClockObject() throws Throwable {
        MutableClock clock = MutableClock.epochUTC();
        Object nonClockObject = new Object();

        boolean isEqual = clock.equals(nonClockObject);

        assertFalse("MutableClock should not be equal to a plain Object", isEqual);
    }
}
