package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test16 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        TextStyle.Builder textStyle_Builder0 = TextStyle.builder();
        TextStyle.Builder textStyle_Builder1 = textStyle_Builder0.setScalable(true);
        assertTrue(textStyle_Builder0.isScalable());
        assertEquals(Integer.MAX_VALUE, textStyle_Builder1.getMaxWidth());
    }
}
