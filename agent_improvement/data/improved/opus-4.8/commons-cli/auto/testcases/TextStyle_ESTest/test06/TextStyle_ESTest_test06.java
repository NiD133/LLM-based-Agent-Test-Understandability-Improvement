package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test06 extends TextStyle_ESTest_scaffolding {

    /**
     * Builds a centered TextStyle with a fixed maximum width, then verifies that
     * the builder retains the configured maximum width and that padding the text
     * runs without error.
     */
    @Test(timeout = 4000)
    public void centeredStyleRetainsConfiguredMaxWidth() throws Throwable {
        final int maxWidth = 3173;

        TextStyle.Builder builder = TextStyle.builder()
                .setMaxWidth(maxWidth)
                .setAlignment(TextStyle.Alignment.CENTER);

        TextStyle style = builder.get();
        style.pad(true, "TextStyle{CENTER, l:0, i:0, true, min:0, max:3173}");

        assertEquals(maxWidth, builder.getMaxWidth());
    }
}
