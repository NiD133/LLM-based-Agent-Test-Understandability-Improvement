package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test21 extends TextStyle_ESTest_scaffolding {

    /**
     * The DEFAULT TextStyle is built from a Builder with no overrides, so its
     * indent should be the default value of 0.
     */
    @Test(timeout = 4000)
    public void defaultTextStyleHasZeroIndent() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;

        int indent = defaultStyle.getIndent();

        assertEquals(0, indent);
    }
}
