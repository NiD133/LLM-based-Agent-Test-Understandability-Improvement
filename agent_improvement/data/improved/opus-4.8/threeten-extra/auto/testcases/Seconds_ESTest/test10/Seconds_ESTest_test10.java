package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test10 extends Seconds_ESTest_scaffolding {

    /**
     * Dividing zero seconds by one yields an amount that is not positive.
     */
    @Test(timeout = 4000)
    public void dividingZeroSecondsByOneIsNotPositive() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;

        Seconds result = zeroSeconds.dividedBy(1);

        assertFalse(result.isPositive());
    }
}
