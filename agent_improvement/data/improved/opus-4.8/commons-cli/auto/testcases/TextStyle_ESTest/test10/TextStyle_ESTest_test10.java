package org.apache.commons.cli.help;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test10 extends TextStyle_ESTest_scaffolding {

    /**
     * Left-aligned padding (the default alignment) should right-fill the text
     * with spaces until it reaches the configured maximum width.
     */
    @Test(timeout = 4000)
    public void padLeftAlignedFillsTrailingSpacesUpToMaxWidth() throws Throwable {
        final int maxWidth = 133;

        TextStyle.Builder builder = TextStyle.builder();
        builder.setMaxWidth(maxWidth);
        TextStyle textStyle = builder.get();

        // 49-character input, shorter than maxWidth, so it gets padded.
        String text = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";
        CharSequence padded = textStyle.pad(false, text);

        assertEquals(maxWidth, builder.getMaxWidth());

        // Original text followed by (133 - 49) = 84 trailing spaces.
        String expected =
                "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}"
                + "                                                                                    ";
        assertEquals(expected, padded);
    }
}
