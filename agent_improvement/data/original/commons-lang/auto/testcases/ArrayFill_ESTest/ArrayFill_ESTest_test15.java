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
public class ArrayFill_ESTest_test15 extends ArrayFill_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        char[] charArray0 = new char[1];
        // Undeclared exception!
        try {
            ArrayFill.clear(charArray0, (int) '-', (int) '-');
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // Array index out of range: 45
            //
            verifyException("java.util.Arrays", e);
        }
    }
}
