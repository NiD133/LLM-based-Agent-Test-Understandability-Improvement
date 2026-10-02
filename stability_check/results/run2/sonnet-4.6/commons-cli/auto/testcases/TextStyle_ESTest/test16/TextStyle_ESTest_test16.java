package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test16 extends TextStyle_ESTest_scaffolding {

    /**
     * Verifies that calling setScalable(true) on a builder enables scaling,
     * and that the default maxWidth remains UNSET_MAX_WIDTH (Integer.MAX_VALUE).
     * setScalable() follows the fluent builder pattern and returns the same instance.
     */
    @Test(timeout = 4000)
    public void test_setScalable_enablesScalingAndPreservesDefaultUnsetMaxWidth() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        // setScalable returns the same builder (fluent API), so both references point to the same object
        TextStyle.Builder sameBuilder = builder.setScalable(true);

        assertTrue(builder.isScalable());
        // Default maxWidth is UNSET_MAX_WIDTH = Integer.MAX_VALUE when no explicit width is set
        assertEquals(Integer.MAX_VALUE, sameBuilder.getMaxWidth());
    }
}
