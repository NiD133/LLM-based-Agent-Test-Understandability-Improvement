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
     * Verifies that {@link TextStyle.Builder#setScalable(boolean)} returns the
     * same builder instance (fluent API) and that enabling scaling leaves the
     * maximum width at its default, unset value ({@link Integer#MAX_VALUE}).
     */
    @Test(timeout = 4000)
    public void setScalableEnablesScalingAndKeepsDefaultMaxWidth() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        TextStyle.Builder returnedBuilder = builder.setScalable(true);

        assertTrue("builder should report scaling as enabled", builder.isScalable());
        assertEquals("max width should remain at its unset default",
                Integer.MAX_VALUE, returnedBuilder.getMaxWidth());
    }
}
