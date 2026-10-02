package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test09 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_abs_ofNegativeYears_returnsPositiveAndLeavesOriginalUnchanged() throws Throwable {
        Years negativeYears = Years.of(-395);
        Years absoluteYears = negativeYears.abs();

        assertEquals(-395, negativeYears.getAmount());
        assertEquals(395, absoluteYears.getAmount());
    }
}
