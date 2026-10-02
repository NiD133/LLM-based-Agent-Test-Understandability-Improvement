package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test22 extends TextStyle_ESTest_scaffolding {

    /**
     * The DEFAULT text style leaves the maximum width unset, so getMaxWidth()
     * should return the sentinel value Integer.MAX_VALUE (UNSET_MAX_WIDTH).
     */
    @Test(timeout = 4000)
    public void getMaxWidth_onDefaultStyle_returnsUnsetMaxWidth() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;

        int maxWidth = defaultStyle.getMaxWidth();

        assertEquals(Integer.MAX_VALUE, maxWidth);
    }
}
