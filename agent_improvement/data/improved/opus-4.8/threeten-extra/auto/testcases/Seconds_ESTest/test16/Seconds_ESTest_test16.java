package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test16 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void isZero_returnsTrue_forZeroSeconds() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;

        boolean isZero = zeroSeconds.isZero();

        assertTrue("Seconds.ZERO should report isZero() == true", isZero);
    }
}
