package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test36 extends StringUtil_ESTest_scaffolding {

    /**
     * A null input string is not numeric, so {@link StringUtil#isNumeric(String)}
     * should return false rather than throw a NullPointerException.
     */
    @Test(timeout = 4000)
    public void isNumericReturnsFalseForNullString() throws Throwable {
        boolean numeric = StringUtil.isNumeric((String) null);

        assertFalse(numeric);
    }
}
