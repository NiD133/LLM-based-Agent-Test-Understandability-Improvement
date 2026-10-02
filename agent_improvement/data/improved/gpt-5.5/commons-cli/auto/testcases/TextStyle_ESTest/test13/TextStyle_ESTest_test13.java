package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test13 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        TextStyle.Builder defaultBuilder = TextStyle.builder();
        int defaultMinWidth = defaultBuilder.getMinWidth();

        assertTrue("Default builder should allow scalable text.", defaultBuilder.isScalable());
        assertEquals("Default max width should be unset.", Integer.MAX_VALUE, defaultBuilder.getMaxWidth());
        assertEquals("Default min width should be zero.", 0, defaultMinWidth);
    }
}
