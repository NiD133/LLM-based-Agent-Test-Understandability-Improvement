package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test02 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testZeroMinutesIsEqualToItself() throws Throwable {
        Minutes zero = Minutes.ZERO;
        boolean isEqualToSelf = zero.equals(zero);
        assertTrue(isEqualToSelf);
    }
}
