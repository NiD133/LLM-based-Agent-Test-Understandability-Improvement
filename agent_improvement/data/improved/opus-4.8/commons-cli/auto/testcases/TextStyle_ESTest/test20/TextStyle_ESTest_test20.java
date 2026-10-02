package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test20 extends TextStyle_ESTest_scaffolding {

    /**
     * The DEFAULT TextStyle should report a minimum width of 0,
     * matching the Builder's default minWidth.
     */
    @Test(timeout = 4000)
    public void defaultStyleHasZeroMinWidth() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;

        int minWidth = defaultStyle.getMinWidth();

        assertEquals(0, minWidth);
    }
}
