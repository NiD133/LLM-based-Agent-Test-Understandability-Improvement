package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test19 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_newBuilder_hasDefaultMaxWidthUnsetAndScalableTrue() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        // A fresh builder should be scalable and have maxWidth set to the sentinel "unset" value
        int maxWidth = builder.getMaxWidth();
        assertTrue(builder.isScalable());
        assertEquals(TextStyle.UNSET_MAX_WIDTH, maxWidth);
    }
}
