package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test10 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Build a TextStyle with maxWidth=133; all other settings remain at defaults
        // (LEFT alignment, leftPad=0, indent=0, scalable=true, minWidth=0)
        final int maxWidth = 133;
        TextStyle.Builder builder = TextStyle.builder();
        builder.setMaxWidth(maxWidth);
        TextStyle textStyle = builder.get();

        // The input is the toString() of a default TextStyle (maxWidth is "unset" there)
        String inputText = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";

        // pad(false, text): addIndent=false means no indent prefix; LEFT alignment
        // appends trailing spaces until the result reaches maxWidth (133 chars)
        CharSequence paddedText = textStyle.pad(false, inputText);

        assertEquals("Builder maxWidth should remain unchanged after build", maxWidth, builder.getMaxWidth());
        assertEquals("LEFT-aligned pad should append trailing spaces to fill maxWidth",
                "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}                                                                                    ",
                paddedText);
    }
}
