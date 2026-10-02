package org.jsoup.internal;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test35 extends StringUtil_ESTest_scaffolding {

    /**
     * An empty string is not numeric, since {@link StringUtil#isNumeric(String)}
     * requires at least one ASCII digit character.
     */
    @Test(timeout = 4000)
    public void emptyStringIsNotNumeric() throws Throwable {
        boolean numeric = StringUtil.isNumeric("");

        assertFalse(numeric);
    }
}
