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
        TextStyle.Builder centeredStyleBuilder = TextStyle.builder();
        TextStyle.Alignment centerAlignment = TextStyle.Alignment.CENTER;
        TextStyle.Builder configuredBuilder = centeredStyleBuilder.setAlignment(centerAlignment);
        CharBuffer emptyText = CharBuffer.allocate(0);

        TextStyle centeredStyle = configuredBuilder.get();
        CharSequence paddedText = centeredStyle.pad(false, emptyText);

        assertEquals("", paddedText);
        assertTrue(centeredStyle.isScalable());
        assertEquals(Integer.MAX_VALUE, centeredStyle.getMaxWidth());
    }
}
