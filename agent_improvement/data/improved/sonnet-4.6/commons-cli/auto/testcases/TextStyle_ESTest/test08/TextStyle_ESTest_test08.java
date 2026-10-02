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

    /**
     * Verifies that padding an empty CharSequence with CENTER alignment and default (unset) maxWidth
     * returns an empty string, and that the default TextStyle is scalable with no fixed max width.
     */
    @Test(timeout = 4000)
    public void test_padEmptyText_withCenterAlignment_andDefaultMaxWidth_returnsEmptyString() throws Throwable {
        TextStyle textStyle = TextStyle.builder()
                .setAlignment(TextStyle.Alignment.CENTER)
                .get();

        CharBuffer emptyBuffer = CharBuffer.allocate(0);
        CharSequence result = textStyle.pad(false, emptyBuffer);

        assertEquals("", result);
        assertTrue(textStyle.isScalable());
        assertEquals(TextStyle.UNSET_MAX_WIDTH, textStyle.getMaxWidth());
    }
}
