package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test06 extends SegmentUtils_ESTest_scaffolding {

    private static final String DESCRIPTOR_WITH_MANY_ARGUMENT_CHARS =
            "Can't ad beyonI end of strea (n = %,d/ coun= %,E, tax-e>gZh % D,d,Aemaining = %,d)";
    private static final int EXPECTED_ARGUMENT_COUNT = 51;

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        int actualArgumentCount = SegmentUtils.countArgs(DESCRIPTOR_WITH_MANY_ARGUMENT_CHARS);

        assertEquals(EXPECTED_ARGUMENT_COUNT, actualArgumentCount);
    }
}
