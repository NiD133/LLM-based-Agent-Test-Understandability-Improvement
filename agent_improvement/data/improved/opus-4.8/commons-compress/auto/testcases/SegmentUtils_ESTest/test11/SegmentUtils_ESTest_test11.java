package org.apache.commons.compress.harmony.unpack200;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test11 extends SegmentUtils_ESTest_scaffolding {

    /**
     * The (deprecated) no-argument constructor should create a SegmentUtils
     * instance without throwing.
     */
    @Test(timeout = 4000)
    public void constructorCreatesInstance() throws Throwable {
        SegmentUtils segmentUtils = new SegmentUtils();

        assertNotNull(segmentUtils);
    }
}
