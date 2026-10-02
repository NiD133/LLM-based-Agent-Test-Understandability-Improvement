package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test18 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void newBuilder_hasScalableTrueAndUnsetMaxWidth() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        assertTrue("A new builder should be scalable by default", builder.isScalable());
        assertEquals("A new builder's maxWidth should be UNSET_MAX_WIDTH by default",
                TextStyle.UNSET_MAX_WIDTH, builder.getMaxWidth());
    }
}
