package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test03 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;
        CharBuffer emptyText = CharBuffer.allocate(0);

        CharSequence paddedText = defaultStyle.pad(false, emptyText);

        assertEquals("", paddedText);
    }
}
