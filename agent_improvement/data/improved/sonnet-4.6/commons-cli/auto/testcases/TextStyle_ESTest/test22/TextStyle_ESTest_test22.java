package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test22 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_defaultStyle_maxWidth_isUnset() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;
        int maxWidth = defaultStyle.getMaxWidth();
        assertEquals(TextStyle.UNSET_MAX_WIDTH, maxWidth);
    }
}
