package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test09 extends TextStyle_ESTest_scaffolding {

    /**
     * Verifies that the maximum width configured on the builder is retained,
     * and that padding text longer than the maximum width leaves it unchanged.
     */
    @Test(timeout = 4000)
    public void setMaxWidthIsRetainedAndPadLeavesLongerTextUnchanged() throws Throwable {
        final int maxWidth = 4;

        TextStyle.Builder builder = TextStyle.builder();
        builder.setMaxWidth(maxWidth);
        TextStyle textStyle = builder.get();

        // The text is longer than maxWidth, so pad() returns it without modification.
        textStyle.pad(true, "TextStyle{LEFT, l:0, i:0, true, min:0, max:4}");

        assertEquals(maxWidth, builder.getMaxWidth());
    }
}
