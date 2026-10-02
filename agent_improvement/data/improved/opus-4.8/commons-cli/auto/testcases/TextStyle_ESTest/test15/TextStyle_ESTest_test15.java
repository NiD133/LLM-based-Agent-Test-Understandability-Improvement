package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test15 extends TextStyle_ESTest_scaffolding {

    /**
     * Verifies that the minimum width set on a TextStyle.Builder is reported back
     * unchanged by getMinWidth().
     */
    @Test(timeout = 4000)
    public void setMinWidthIsReturnedByGetMinWidth() throws Throwable {
        final int expectedMinWidth = 3329;

        TextStyle.Builder builder = TextStyle.builder();
        builder.setMinWidth(expectedMinWidth);

        assertEquals(expectedMinWidth, builder.getMinWidth());
    }
}
