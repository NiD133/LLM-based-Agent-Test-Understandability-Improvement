package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test24 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        Seconds zeroMinutesAsSeconds = Seconds.ofMinutes(0);
        assertTrue(zeroMinutesAsSeconds.isZero());
    }
}
