package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test19 extends TextStyle_ESTest_scaffolding {

    /**
     * A freshly created builder should expose the documented default values:
     * the maximum width is unset (Integer.MAX_VALUE) and scaling is enabled.
     */
    @Test(timeout = 4000)
    public void newBuilderHasUnsetMaxWidthAndIsScalable() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        int defaultMaxWidth = builder.getMaxWidth();

        assertTrue("a new builder should be scalable by default", builder.isScalable());
        assertEquals("an unset maximum width should be Integer.MAX_VALUE",
                Integer.MAX_VALUE, defaultMaxWidth);
    }
}
