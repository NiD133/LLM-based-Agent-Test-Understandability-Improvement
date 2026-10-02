package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test07 extends SegmentUtils_ESTest_scaffolding {

    private static final String DESCRIPTOR_WITH_SEVEN_ARGUMENT_CHARS = "(0ANr[&rF)8mUn?";
    private static final int EXPECTED_ARGUMENT_COUNT = 7;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        int argumentCount = SegmentUtils.countArgs(DESCRIPTOR_WITH_SEVEN_ARGUMENT_CHARS);

        assertEquals(EXPECTED_ARGUMENT_COUNT, argumentCount);
    }
}
