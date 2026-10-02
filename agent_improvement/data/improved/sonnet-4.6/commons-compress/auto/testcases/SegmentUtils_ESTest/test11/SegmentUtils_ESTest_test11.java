package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test11 extends SegmentUtils_ESTest_scaffolding {

    /**
     * Verifies that the deprecated default constructor of SegmentUtils
     * successfully creates a non-null instance.
     */
    @Test(timeout = 4000)
    public void testDefaultConstructorCreatesInstance() throws Throwable {
        SegmentUtils segmentUtils = new SegmentUtils();
        assertNotNull(segmentUtils);
    }
}
