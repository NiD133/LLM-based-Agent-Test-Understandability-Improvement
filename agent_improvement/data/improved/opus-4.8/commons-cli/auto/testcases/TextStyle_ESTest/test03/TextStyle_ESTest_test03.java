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

    /**
     * Padding empty text with the DEFAULT style yields an empty result.
     *
     * <p>The DEFAULT style uses an unset maximum width and zero indent, so there is
     * nothing to pad. With empty input text the padded output is therefore also empty.</p>
     */
    @Test(timeout = 4000)
    public void padEmptyTextWithDefaultStyleReturnsEmptyString() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;
        CharBuffer emptyText = CharBuffer.allocate(0);

        CharSequence padded = defaultStyle.pad(false, emptyText);

        assertEquals("", padded);
    }
}
