package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test10 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void dividingZeroSecondsByOne_isNotPositive() throws Throwable {
        Seconds zero = Seconds.ZERO;
        Seconds result = zero.dividedBy(1);
        assertFalse("0 divided by 1 is still 0, which is not positive", result.isPositive());
    }
}
