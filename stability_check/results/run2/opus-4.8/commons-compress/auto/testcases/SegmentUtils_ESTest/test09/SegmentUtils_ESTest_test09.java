package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test09 extends SegmentUtils_ESTest_scaffolding {

    /**
     * countArgs rejects a descriptor whose closing parenthesis appears before
     * the opening one (here ")" is at index 0 while "(" is at index 8). Because
     * the parentheses do not enclose a valid argument list, the method throws an
     * IllegalArgumentException ("No arguments").
     */
    @Test(timeout = 4000)
    public void countArgsThrowsWhenParenthesesAreOutOfOrder() throws Throwable {
        String malformedDescriptor = ")}WJ,:qJ(Hxxh";
        try {
            SegmentUtils.countArgs(malformedDescriptor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Thrown by SegmentUtils.countArgs with the message "No arguments".
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentUtils", e);
        }
    }
}
