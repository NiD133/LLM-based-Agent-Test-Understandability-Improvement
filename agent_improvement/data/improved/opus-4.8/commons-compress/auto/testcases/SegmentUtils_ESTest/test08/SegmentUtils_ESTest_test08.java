package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test08 extends SegmentUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link SegmentUtils#countArgs(String)} counts a single
     * object-typed argument in a method descriptor as one argument.
     * The descriptor "(Ljava/lang/Object;)Ljava/lang/Object;" represents a
     * method taking one Object and returning an Object.
     */
    @Test(timeout = 4000)
    public void countArgs_withSingleObjectArgument_returnsOne() throws Throwable {
        String singleObjectArgDescriptor = "(Ljava/lang/Object;)Ljava/lang/Object;";

        int argumentCount = SegmentUtils.countArgs(singleObjectArgDescriptor);

        assertEquals(1, argumentCount);
    }
}
