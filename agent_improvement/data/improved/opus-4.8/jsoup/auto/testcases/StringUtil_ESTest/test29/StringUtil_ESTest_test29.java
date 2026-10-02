package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test29 extends StringUtil_ESTest_scaffolding {

    /**
     * The tab character (code point 9, '\t') should be recognised as whitespace
     * by {@link StringUtil#isActuallyWhitespace(int)}.
     */
    @Test(timeout = 4000)
    public void isActuallyWhitespace_returnsTrue_forTabCharacter() throws Throwable {
        int tabCodePoint = '\t'; // code point 9

        boolean isWhitespace = StringUtil.isActuallyWhitespace(tabCodePoint);

        assertTrue("Tab character should be treated as whitespace", isWhitespace);
    }
}
