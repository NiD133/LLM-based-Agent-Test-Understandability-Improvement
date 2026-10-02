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
        TextStyle.Builder builder = TextStyle.builder();
        TextStyle.Alignment centerAlignment = TextStyle.Alignment.CENTER;
        TextStyle.Builder centeredBuilder = builder.setAlignment(centerAlignment);
        CharBuffer emptyText = CharBuffer.allocate(0);

        TextStyle centeredDefaultStyle = centeredBuilder.get();
        CharSequence paddedText = centeredDefaultStyle.pad(false, emptyText);

        assertEquals("", paddedText);
        assertTrue(centeredDefaultStyle.isScalable());
        assertEquals(Integer.MAX_VALUE, centeredDefaultStyle.getMaxWidth());
    }
}
