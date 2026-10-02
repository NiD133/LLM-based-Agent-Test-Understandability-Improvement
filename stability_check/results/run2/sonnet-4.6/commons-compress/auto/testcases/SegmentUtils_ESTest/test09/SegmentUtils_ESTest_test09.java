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

    // A descriptor where ')' appears before '(' is structurally invalid;
    // countArgs requires a well-formed "(args)returnType" descriptor.
    private static final String DESCRIPTOR_WITH_CLOSING_BEFORE_OPENING_PAREN = ")}WJ,:qJ(Hxxh";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        try {
            SegmentUtils.countArgs(DESCRIPTOR_WITH_CLOSING_BEFORE_OPENING_PAREN);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentUtils", e);
        }
    }
}
