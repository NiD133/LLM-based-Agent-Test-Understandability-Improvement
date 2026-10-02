package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test04 extends TextStyle_ESTest_scaffolding {

    private static final int CONFIGURED_MAX_WIDTH = 1132;
    private static final String TEXT_TO_PAD = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        builder.setMaxWidth(CONFIGURED_MAX_WIDTH);

        TextStyle style = builder.get();
        style.pad(true, TEXT_TO_PAD);

        assertEquals(CONFIGURED_MAX_WIDTH, builder.getMaxWidth());
    }
}
