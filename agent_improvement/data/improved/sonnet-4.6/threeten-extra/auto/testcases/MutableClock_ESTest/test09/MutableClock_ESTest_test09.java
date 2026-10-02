package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test09 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_epochUTC_toString_returnsExpectedFormat() throws Throwable {
        MutableClock clock = MutableClock.epochUTC();
        String clockString = clock.toString();
        assertEquals("MutableClock[1970-01-01T00:00:00Z,Z]", clockString);
    }
}
