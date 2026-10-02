package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test09 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        TextStyle.Builder builderAfterMaxWidthUpdate = builder.setMaxWidth(4);
        TextStyle styleWithMaxWidthFour = builderAfterMaxWidthUpdate.get();
        CharSequence textLongerThanMaxWidth = "TextStyle{LEFT, l:0, i:0, true, min:0, max:4}";

        styleWithMaxWidthFour.pad(true, textLongerThanMaxWidth);

        assertEquals(4, builder.getMaxWidth());
    }
}
