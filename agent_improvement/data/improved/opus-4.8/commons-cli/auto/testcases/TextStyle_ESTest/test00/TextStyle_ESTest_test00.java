package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test00 extends TextStyle_ESTest_scaffolding {

    /**
     * The default TextStyle should render its property values via toString():
     * LEFT alignment, leftPad 0, indent 0, scalable true, minWidth 0, and an
     * unset maxWidth.
     */
    @Test(timeout = 4000)
    public void toStringOfDefaultStyleListsDefaultProperties() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;

        String description = defaultStyle.toString();

        assertEquals("TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}", description);
    }
}
