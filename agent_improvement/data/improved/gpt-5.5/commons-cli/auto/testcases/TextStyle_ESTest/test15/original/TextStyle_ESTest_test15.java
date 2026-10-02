package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test15 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        TextStyle.Builder textStyle_Builder0 = TextStyle.builder();
        textStyle_Builder0.setMinWidth(3329);
        assertEquals(3329, textStyle_Builder0.getMinWidth());
    }
}
