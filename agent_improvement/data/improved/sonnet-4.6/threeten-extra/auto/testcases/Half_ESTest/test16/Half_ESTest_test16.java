package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test16 extends Half_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_fromHalf_returnsSameInstance() throws Throwable {
        Half secondHalf = Half.H2;
        Half result = Half.from(secondHalf);
        // Half.from() must return the identical enum instance when given a Half
        assertSame(result, secondHalf);
    }
}
