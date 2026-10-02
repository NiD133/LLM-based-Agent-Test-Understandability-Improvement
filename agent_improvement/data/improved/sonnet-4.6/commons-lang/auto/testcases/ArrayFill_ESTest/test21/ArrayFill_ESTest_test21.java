package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArrayFill_ESTest_test21 extends ArrayFill_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_clearCharArray_returnsNullWhenInputIsNull() throws Throwable {
        char[] result = ArrayFill.clear((char[]) null);
        assertNull(result);
    }
}
