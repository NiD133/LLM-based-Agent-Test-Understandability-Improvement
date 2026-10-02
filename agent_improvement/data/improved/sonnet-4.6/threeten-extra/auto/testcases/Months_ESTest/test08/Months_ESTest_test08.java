package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test08 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Months oneMonth = Months.ONE;
        Months absOfOneMonth = oneMonth.abs();
        assertTrue(absOfOneMonth.isPositive());
    }
}
