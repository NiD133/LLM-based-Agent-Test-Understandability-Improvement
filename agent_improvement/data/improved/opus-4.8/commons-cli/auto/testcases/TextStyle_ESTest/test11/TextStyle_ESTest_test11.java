package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test11 extends TextStyle_ESTest_scaffolding {

    /**
     * A freshly created builder should expose its documented defaults:
     * leftPad = 0, maxWidth = Integer.MAX_VALUE (unset), and scalable = true.
     */
    @Test(timeout = 4000)
    public void newBuilderExposesDefaultValues() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();

        assertEquals("default leftPad", 0, builder.getLeftPad());
        assertEquals("default maxWidth (unset)", Integer.MAX_VALUE, builder.getMaxWidth());
        assertTrue("scalable by default", builder.isScalable());
    }
}
