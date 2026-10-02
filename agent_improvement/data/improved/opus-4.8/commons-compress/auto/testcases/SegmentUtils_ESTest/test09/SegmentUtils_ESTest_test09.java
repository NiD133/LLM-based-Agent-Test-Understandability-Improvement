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
     * When the descriptor's closing parenthesis ')' appears before its opening
     * parenthesis '(', the argument list is malformed and {@link SegmentUtils#countArgs(String)}
     * must reject it with an IllegalArgumentException ("No arguments").
     */
    @Test(timeout = 4000)
    public void countArgsRejectsDescriptorWithParenthesesOutOfOrder() throws Throwable {
        // ')' is at index 1, '(' is at index 9 -> closing comes before opening.
        String descriptorWithParensOutOfOrder = ")}WJ,:qJ(Hxxh";

        try {
            SegmentUtils.countArgs(descriptorWithParensOutOfOrder);
            fail("Expected IllegalArgumentException for a descriptor with no valid argument list");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentUtils", e);
        }
    }
}
