package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test09 extends IEEE754rUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        double minimum = IEEE754rUtils.min((-1.0), (-1.0), 0.0);

        assertEquals((-1.0), minimum, 0.01);
    }
}
