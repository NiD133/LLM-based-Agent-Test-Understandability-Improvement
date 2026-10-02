package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test12 extends SegmentUtils_ESTest_scaffolding {

    /**
     * A descriptor missing a closing parenthesis (e.g. "(+,") cannot be parsed,
     * so countInvokeInterfaceArgs must reject it with IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void test_countInvokeInterfaceArgs_descriptorMissingClosingParen_throwsIllegalArgumentException() throws Throwable {
        String descriptorWithNoClosingParen = "(+,";

        try {
            SegmentUtils.countInvokeInterfaceArgs(descriptorWithNoClosingParen);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentUtils", e);
        }
    }
}
