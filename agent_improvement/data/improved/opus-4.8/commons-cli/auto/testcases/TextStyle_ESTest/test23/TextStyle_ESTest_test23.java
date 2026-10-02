package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test23 extends TextStyle_ESTest_scaffolding {

    /**
     * The DEFAULT text style should have a left padding of zero,
     * matching the builder default for leftPad.
     */
    @Test(timeout = 4000)
    public void defaultStyleHasZeroLeftPad() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;

        int leftPad = defaultStyle.getLeftPad();

        assertEquals(0, leftPad);
    }
}
