package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test10 extends IEEE754rUtils_ESTest_scaffolding {

    private static final double LARGEST_VALUE = 734.1;
    private static final double MIDDLE_VALUE = 331.69049072265625;
    private static final double SMALLEST_VALUE = (double) 0.0F;
    private static final double ASSERTION_DELTA = 0.01;

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        double maximum = IEEE754rUtils.max(LARGEST_VALUE, MIDDLE_VALUE, SMALLEST_VALUE);

        assertEquals(LARGEST_VALUE, maximum, ASSERTION_DELTA);
    }
}
