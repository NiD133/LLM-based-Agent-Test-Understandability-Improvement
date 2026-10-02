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
     * A freshly created builder should expose the documented default values:
     * scalable = true, minWidth = 0 and maxWidth = UNSET_MAX_WIDTH (Integer.MAX_VALUE).
     */
    @Test(timeout = 4000)
    public void newBuilderHasDocumentedDefaults() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        assertTrue("a new builder should be scalable by default", builder.isScalable());
        assertEquals("default minWidth should be 0", 0, builder.getMinWidth());
        assertEquals("default maxWidth should be unset (Integer.MAX_VALUE)",
                Integer.MAX_VALUE, builder.getMaxWidth());
    }
}
