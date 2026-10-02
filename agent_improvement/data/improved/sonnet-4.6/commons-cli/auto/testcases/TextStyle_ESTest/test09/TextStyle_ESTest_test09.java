package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test09 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        final int expectedMaxWidth = 4;

        TextStyle.Builder builder = TextStyle.builder();
        builder.setMaxWidth(expectedMaxWidth);
        TextStyle style = builder.get();

        // Text longer than maxWidth is returned unchanged by pad(); the builder's maxWidth should remain 4
        style.pad(true, "TextStyle{LEFT, l:0, i:0, true, min:0, max:4}");

        assertEquals(expectedMaxWidth, builder.getMaxWidth());
    }
}
