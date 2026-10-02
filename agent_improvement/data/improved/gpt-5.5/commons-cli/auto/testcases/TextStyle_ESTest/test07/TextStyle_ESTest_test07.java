package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test07 extends TextStyle_ESTest_scaffolding {

    private static final String UNPADDED_TEXT = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        TextStyle.Alignment rightAlignment = TextStyle.Alignment.RIGHT;
        TextStyle.Builder rightAlignedBuilder = builder.setAlignment(rightAlignment);
        TextStyle rightAlignedStyle = rightAlignedBuilder.get();

        CharSequence paddedText = rightAlignedStyle.pad(true, UNPADDED_TEXT);

        assertEquals(Integer.MAX_VALUE, rightAlignedStyle.getMaxWidth());
        assertTrue(rightAlignedStyle.isScalable());
        assertEquals(UNPADDED_TEXT, paddedText);
    }
}
