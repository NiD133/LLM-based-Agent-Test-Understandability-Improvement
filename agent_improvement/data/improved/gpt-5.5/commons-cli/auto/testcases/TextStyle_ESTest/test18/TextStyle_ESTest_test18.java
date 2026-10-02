package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test18 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        TextStyle.Builder defaultStyleBuilder = TextStyle.builder();

        boolean scalableByDefault = defaultStyleBuilder.isScalable();

        assertTrue("A new builder should be scalable by default.", scalableByDefault);
        assertEquals("A new builder should leave max width unset.", Integer.MAX_VALUE, defaultStyleBuilder.getMaxWidth());
    }
}
