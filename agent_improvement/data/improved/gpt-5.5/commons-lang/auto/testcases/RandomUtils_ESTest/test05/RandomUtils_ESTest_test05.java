package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.security.SecureRandom;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test05 extends RandomUtils_ESTest_scaffolding {

    private static final double ZERO_WIDTH_BOUND = 0.0;
    private static final double DOUBLE_ASSERTION_DELTA = 0.01;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        double valueFromZeroWidthRange = RandomUtils.nextDouble(ZERO_WIDTH_BOUND, ZERO_WIDTH_BOUND);

        assertEquals(ZERO_WIDTH_BOUND, valueFromZeroWidthRange, DOUBLE_ASSERTION_DELTA);
    }
}
