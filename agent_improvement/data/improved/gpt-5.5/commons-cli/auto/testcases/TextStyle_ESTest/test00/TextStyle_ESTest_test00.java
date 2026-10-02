package org.apache.commons.cli.help;

import static org.junit.Assert.*;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test00 extends TextStyle_ESTest_scaffolding {

    private static final String DEFAULT_TEXT_STYLE_DESCRIPTION = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        TextStyle defaultTextStyle = TextStyle.DEFAULT;

        String description = defaultTextStyle.toString();

        assertEquals(DEFAULT_TEXT_STYLE_DESCRIPTION, description);
    }
}
