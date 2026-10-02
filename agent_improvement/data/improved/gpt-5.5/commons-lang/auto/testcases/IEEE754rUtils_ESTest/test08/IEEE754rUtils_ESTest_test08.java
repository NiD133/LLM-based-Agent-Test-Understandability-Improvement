package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test08 extends IEEE754rUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        final float repeatedLargeValue = 1488.587F;
        final float smallerValue = 0.0F;

        final float minimum = IEEE754rUtils.min(repeatedLargeValue, smallerValue, repeatedLargeValue);

        assertEquals(smallerValue, minimum, 0.01F);
    }
}
