package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test05 extends StringUtil_ESTest_scaffolding {

    /**
     * A whitespace-only string is not numeric, because spaces are not digit characters.
     */
    @Test(timeout = 4000)
    public void isNumericReturnsFalseForWhitespaceOnlyString() throws Throwable {
        boolean isNumeric = StringUtil.isNumeric("    ");

        assertFalse(isNumeric);
    }
}
