package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Arrays;
import org.apache.commons.lang3.function.FailableIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test01 extends ArrayFill_ESTest_scaffolding {

    // Verifies that fill() returns null without throwing when the input array is null.
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Object fillValue = new Object();
        Object[] result = ArrayFill.fill((Object[]) null, fillValue);
        assertNull(result);
    }
}
