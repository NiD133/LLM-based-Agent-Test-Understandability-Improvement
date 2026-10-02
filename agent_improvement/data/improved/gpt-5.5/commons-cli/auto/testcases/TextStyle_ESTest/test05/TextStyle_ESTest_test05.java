package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test05 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        TextStyle.Alignment centerAlignment = TextStyle.Alignment.CENTER;
        TextStyle.Builder centeredBuilder = builder.setAlignment(centerAlignment);
        TextStyle centeredStyle = centeredBuilder.get();

        String originalText = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";
        CharSequence paddedText = centeredStyle.pad(true, originalText);

        assertEquals(Integer.MAX_VALUE, centeredStyle.getMaxWidth());
        assertTrue(centeredStyle.isScalable());
        assertEquals("TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}", paddedText);
    }
}
