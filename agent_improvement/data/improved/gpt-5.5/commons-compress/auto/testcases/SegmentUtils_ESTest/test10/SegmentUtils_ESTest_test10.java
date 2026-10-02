package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test10 extends SegmentUtils_ESTest_scaffolding {

    private static final String DESCRIPTOR_WITHOUT_ARGUMENT_LIST = "";
    private static final int WIDTH_OF_LONGS_AND_DOUBLES = 1740;

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        try {
            SegmentUtils.countArgs(DESCRIPTOR_WITHOUT_ARGUMENT_LIST, WIDTH_OF_LONGS_AND_DOUBLES);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentUtils", e);
        }
    }
}
