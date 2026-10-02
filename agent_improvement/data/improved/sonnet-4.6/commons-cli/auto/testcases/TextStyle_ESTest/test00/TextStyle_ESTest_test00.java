package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test00 extends TextStyle_ESTest_scaffolding {

    /**
     * Verifies that the DEFAULT TextStyle instance has the expected default field values:
     * alignment=LEFT, leftPad=0, indent=0, scalable=true, minWidth=0, maxWidth=unset.
     */
    @Test(timeout = 4000)
    public void test_defaultTextStyle_toStringShowsDefaultFieldValues() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;

        String result = defaultStyle.toString();

        assertEquals("TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}", result);
    }
}
