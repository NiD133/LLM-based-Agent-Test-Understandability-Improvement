package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test13 extends TextStyle_ESTest_scaffolding {

    /**
     * Verifies that a freshly created TextStyle.Builder has the expected default values:
     * minWidth = 0, maxWidth = UNSET_MAX_WIDTH (Integer.MAX_VALUE), and scalable = true.
     */
    @Test(timeout = 4000)
    public void test_builderDefaultValues_minWidthIsZeroMaxWidthIsUnsetAndScalableIsTrue() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        int defaultMinWidth = builder.getMinWidth();

        assertEquals("Default minWidth should be 0", 0, defaultMinWidth);
        assertEquals("Default maxWidth should be UNSET_MAX_WIDTH (Integer.MAX_VALUE)",
                Integer.MAX_VALUE, builder.getMaxWidth());
        assertTrue("Builder should be scalable by default", builder.isScalable());
    }
}
