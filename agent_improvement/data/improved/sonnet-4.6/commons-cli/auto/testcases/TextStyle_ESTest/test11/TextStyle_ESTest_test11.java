package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test11 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11_newBuilderHasDefaultLeftPadMaxWidthAndScalable() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        int defaultLeftPad = builder.getLeftPad();
        assertEquals(TextStyle.UNSET_MAX_WIDTH, builder.getMaxWidth());
        assertEquals(0, defaultLeftPad);
        assertTrue(builder.isScalable());
    }
}
