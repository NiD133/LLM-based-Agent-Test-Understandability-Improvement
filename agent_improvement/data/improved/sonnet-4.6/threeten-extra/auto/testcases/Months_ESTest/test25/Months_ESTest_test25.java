package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test25 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        // Adding ONE to ZERO should return the singleton ONE instance (0 + 1 = 1)
        Months oneMonth = Months.ONE;
        Months result = Months.ZERO.plus(oneMonth);
        assertSame(result, oneMonth);
    }
}
