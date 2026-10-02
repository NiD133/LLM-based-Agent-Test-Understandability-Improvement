package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test08 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Build a TextStyle with CENTER alignment and default width settings
        TextStyle centeredStyle = TextStyle.builder()
                .setAlignment(TextStyle.Alignment.CENTER)
                .get();

        // Padding an empty CharBuffer should return an empty string
        CharBuffer emptyBuffer = CharBuffer.allocate(0);
        CharSequence paddedResult = centeredStyle.pad(false, emptyBuffer);

        assertEquals("", paddedResult);
        // Scalable defaults to true and maxWidth defaults to Integer.MAX_VALUE (unset)
        assertTrue(centeredStyle.isScalable());
        assertEquals(Integer.MAX_VALUE, centeredStyle.getMaxWidth());
    }
}
