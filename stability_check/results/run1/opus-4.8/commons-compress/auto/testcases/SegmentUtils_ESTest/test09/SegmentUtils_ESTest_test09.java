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
     * countArgs requires a well-formed descriptor containing '(' before ')'.
     * Here the closing parenthesis appears before the opening one, so the
     * method rejects the input with an IllegalArgumentException ("No arguments").
     */
    @Test(timeout = 4000)
    public void countArgsWithClosingBeforeOpeningParenThrows() throws Throwable {
        String malformedDescriptor = ")}WJ,:qJ(Hxxh";
        try {
            SegmentUtils.countArgs(malformedDescriptor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Thrown by SegmentUtils.countArgs because ')' precedes '('.
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentUtils", e);
        }
    }
}
