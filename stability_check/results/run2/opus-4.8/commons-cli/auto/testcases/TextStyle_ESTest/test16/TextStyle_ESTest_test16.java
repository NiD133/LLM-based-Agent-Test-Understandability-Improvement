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
     * Verifies that {@code setScalable} is a fluent setter that returns the same builder
     * instance, and that enabling scalability leaves the maximum width at its default
     * (unset) value of {@link Integer#MAX_VALUE}.
     */
    @Test(timeout = 4000)
    public void setScalableTrueKeepsMaxWidthUnsetAndReturnsSameBuilder() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        TextStyle.Builder returnedBuilder = builder.setScalable(true);

        assertTrue("scalable flag should be enabled after setScalable(true)", builder.isScalable());
        assertEquals("max width should remain at its unset default", Integer.MAX_VALUE, returnedBuilder.getMaxWidth());
    }
}
