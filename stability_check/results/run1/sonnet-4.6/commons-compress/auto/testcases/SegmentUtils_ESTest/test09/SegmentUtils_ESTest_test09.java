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
     * A descriptor where ')' appears before '(' is malformed and must be rejected.
     * The input ")}WJ,:qJ(Hxxh" has ')' at index 0 and '(' at index 8, so ket < bra,
     * which triggers the "No arguments" IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void test09_countArgs_throwsWhenClosingParenPrecedesOpeningParen() throws Throwable {
        String malformedDescriptor = ")}WJ,:qJ(Hxxh";

        try {
            SegmentUtils.countArgs(malformedDescriptor);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentUtils", e);
        }
    }
}
