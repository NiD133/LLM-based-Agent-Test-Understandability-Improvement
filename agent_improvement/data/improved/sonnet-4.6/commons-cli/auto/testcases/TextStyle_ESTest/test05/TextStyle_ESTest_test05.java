package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test05 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_padWithCenterAlignmentAndUnsetMaxWidth_returnsTextUnchanged() throws Throwable {
        // Build a TextStyle with CENTER alignment; all other properties remain at defaults
        // (leftPad=0, indent=0, scalable=true, minWidth=0, maxWidth=UNSET)
        TextStyle centeredStyle = TextStyle.builder()
                .setAlignment(TextStyle.Alignment.CENTER)
                .get();

        // The input string is longer than Integer.MAX_VALUE would ever be reached
        // but since maxWidth is UNSET, pad() returns the text unchanged with no padding
        String inputText = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";
        CharSequence paddedResult = centeredStyle.pad(true, inputText);

        // When maxWidth is unset and indent is 0, CENTER alignment adds no padding
        assertEquals(inputText, paddedResult);

        // Verify the default property values of the built TextStyle
        assertEquals(Integer.MAX_VALUE, centeredStyle.getMaxWidth());
        assertTrue(centeredStyle.isScalable());
    }
}
