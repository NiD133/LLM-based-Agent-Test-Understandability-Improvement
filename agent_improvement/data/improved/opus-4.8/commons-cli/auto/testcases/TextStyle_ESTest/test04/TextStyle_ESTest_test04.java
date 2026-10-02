package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test04 extends TextStyle_ESTest_scaffolding {

    /**
     * Verifies that a max width configured on the Builder is retained, and that
     * padding a shorter-than-max text with the resulting TextStyle runs without error.
     */
    @Test(timeout = 4000)
    public void padTextWithConfiguredMaxWidth() throws Throwable {
        final int maxWidth = 1132;

        TextStyle.Builder builder = TextStyle.builder();
        builder.setMaxWidth(maxWidth);
        TextStyle textStyle = builder.get();

        // Pad a text shorter than the configured max width (addIndent = true).
        textStyle.pad(true, "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}");

        assertEquals(maxWidth, builder.getMaxWidth());
    }
}
