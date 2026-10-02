package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test10 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        final int configuredMaxWidth = 133;
        final String textToPad = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";
        final String expectedPaddedText =
                "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}                                                                                    ";

        TextStyle.Builder builder = TextStyle.builder();
        builder.setMaxWidth(configuredMaxWidth);
        TextStyle textStyle = builder.get();
        CharSequence paddedText = textStyle.pad(false, textToPad);

        assertEquals(configuredMaxWidth, builder.getMaxWidth());
        assertEquals(expectedPaddedText, paddedText);
    }
}
