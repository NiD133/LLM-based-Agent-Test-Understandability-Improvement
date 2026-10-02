package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test07 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_padWithRightAlignmentAndUnsetMaxWidthReturnsTextUnchanged() throws Throwable {
        // Build a TextStyle with RIGHT alignment; all other settings remain at defaults
        // (leftPad=0, indent=0, scalable=true, minWidth=0, maxWidth=UNSET_MAX_WIDTH)
        TextStyle style = TextStyle.builder()
                .setAlignment(TextStyle.Alignment.RIGHT)
                .get();

        // When maxWidth is unset (Integer.MAX_VALUE) the pad method returns the text as-is
        String inputText = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";
        CharSequence paddedText = style.pad(true, inputText);

        assertEquals(Integer.MAX_VALUE, style.getMaxWidth());
        assertTrue(style.isScalable());
        assertEquals(inputText, paddedText);
    }
}
