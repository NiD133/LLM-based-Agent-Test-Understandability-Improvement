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
public class RandomUtils_ESTest_test10 extends RandomUtils_ESTest_scaffolding {

    // When start equals end, nextFloat must return exactly that value (no range to randomize).
    private static final float FIXED_FLOAT_VALUE = 3.9726779E18F;

    @Test(timeout = 4000)
    public void test10_nextFloat_whenStartEqualsEnd_returnsExactBoundaryValue() throws Throwable {
        float result = RandomUtils.nextFloat(FIXED_FLOAT_VALUE, FIXED_FLOAT_VALUE);
        assertEquals(FIXED_FLOAT_VALUE, result, 0.01F);
    }
}
