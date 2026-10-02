package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test08 extends SegmentUtils_ESTest_scaffolding {

    // A method descriptor for a method that takes one Object parameter and returns an Object:
    // "(Ljava/lang/Object;)Ljava/lang/Object;" — one reference-type argument between the parentheses.
    private static final String SINGLE_OBJECT_PARAM_DESCRIPTOR = "(Ljava/lang/Object;)Ljava/lang/Object;";

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        int argCount = SegmentUtils.countArgs(SINGLE_OBJECT_PARAM_DESCRIPTOR);
        assertEquals("A descriptor with one Object parameter should report exactly 1 argument", 1, argCount);
    }
}
