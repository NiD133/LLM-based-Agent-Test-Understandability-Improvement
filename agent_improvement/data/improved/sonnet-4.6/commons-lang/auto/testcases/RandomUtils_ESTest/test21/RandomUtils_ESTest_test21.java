package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test21 extends RandomUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21_nextFloatReturnsValueInValidRange() throws Throwable {
        float result = RandomUtils.nextFloat();

        assertTrue("nextFloat() result must be non-negative", result >= 0f);
        assertTrue("nextFloat() result must be less than Float.MAX_VALUE", result < Float.MAX_VALUE);
    }
}
