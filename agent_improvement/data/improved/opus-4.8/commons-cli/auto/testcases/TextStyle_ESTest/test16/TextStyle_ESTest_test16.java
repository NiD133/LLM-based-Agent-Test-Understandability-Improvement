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
     * Verifies that {@link TextStyle.Builder#setScalable(boolean)} is a fluent
     * setter: it updates the builder's scalable flag and returns the same builder
     * instance, which still carries the default (unset) maximum width.
     */
    @Test(timeout = 4000)
    public void setScalableUpdatesFlagAndReturnsSameBuilderWithDefaultMaxWidth() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        TextStyle.Builder returnedBuilder = builder.setScalable(true);

        assertTrue("scalable flag should be set to true", builder.isScalable());
        assertEquals("max width should remain at its unset default",
                Integer.MAX_VALUE, returnedBuilder.getMaxWidth());
    }
}
