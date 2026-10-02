package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test18 extends TextStyle_ESTest_scaffolding {

    /**
     * A freshly created {@link TextStyle.Builder} should expose its documented
     * defaults: scaling is enabled and the maximum width is unset
     * ({@link Integer#MAX_VALUE}).
     */
    @Test(timeout = 4000)
    public void newBuilderHasScalableEnabledAndUnsetMaxWidth() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        assertTrue("a new builder should be scalable by default", builder.isScalable());
        assertEquals("a new builder should have an unset (max) width",
                Integer.MAX_VALUE, builder.getMaxWidth());
    }
}
