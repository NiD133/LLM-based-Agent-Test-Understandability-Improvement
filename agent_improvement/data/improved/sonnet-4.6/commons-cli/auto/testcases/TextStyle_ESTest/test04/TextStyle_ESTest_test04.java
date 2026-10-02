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
     * Verifies that building a TextStyle from a Builder does not mutate the Builder's
     * maxWidth setting, and that calling pad() on the built TextStyle completes without
     * error when the text is shorter than the configured maxWidth.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        final int maxWidth = 1132;

        TextStyle.Builder builder = TextStyle.builder();
        builder.setMaxWidth(maxWidth);

        // Build the TextStyle; the builder's configuration should remain unchanged
        TextStyle textStyle = builder.get();

        // pad() is called with a text shorter than maxWidth=1132; it should return without error
        textStyle.pad(true, "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}");

        // Confirm the builder still reports the originally configured maxWidth
        assertEquals(maxWidth, builder.getMaxWidth());
    }
}
