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

    private static final String DESCRIPTOR_WITH_CLOSING_PARENTHESIS_FIRST = ")}WJ,:qJ(Hxxh";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        try {
            SegmentUtils.countArgs(DESCRIPTOR_WITH_CLOSING_PARENTHESIS_FIRST);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentUtils", exception);
        }
    }
}
