package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test14 extends TextStyle_ESTest_scaffolding {

    /**
     * A freshly created Builder should expose the documented default values:
     * an indent of 0, a maximum width of Integer.MAX_VALUE (the "unset" value),
     * and scaling enabled.
     */
    @Test(timeout = 4000)
    public void builderHasExpectedDefaults() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        assertEquals("default indent should be 0", 0, builder.getIndent());
        assertEquals("default maxWidth should be unset (Integer.MAX_VALUE)",
                Integer.MAX_VALUE, builder.getMaxWidth());
        assertTrue("a new builder should be scalable by default", builder.isScalable());
    }
}
