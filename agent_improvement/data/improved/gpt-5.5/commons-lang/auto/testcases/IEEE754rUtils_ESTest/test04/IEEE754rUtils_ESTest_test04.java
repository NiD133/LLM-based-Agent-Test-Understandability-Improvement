package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test04 extends IEEE754rUtils_ESTest_scaffolding {

    private static final float DEFAULT_FLOAT_VALUE = 0.0F;
    private static final float ASSERTION_DELTA = 0.01F;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        final float[] defaultInitializedValues = new float[3];

        final float maximumValue = IEEE754rUtils.max(defaultInitializedValues);

        assertEquals(DEFAULT_FLOAT_VALUE, maximumValue, ASSERTION_DELTA);
    }
}
