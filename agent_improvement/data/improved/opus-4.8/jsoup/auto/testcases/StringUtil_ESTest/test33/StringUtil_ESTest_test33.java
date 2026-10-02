package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test33 extends StringUtil_ESTest_scaffolding {

    /**
     * The tab character ('\t', code point 9) is whitespace per the HTML spec,
     * so {@link StringUtil#isWhitespace(int)} should report it as such.
     */
    @Test(timeout = 4000)
    public void isWhitespaceReturnsTrueForTabCodePoint() throws Throwable {
        int tabCodePoint = '\t'; // == 9

        boolean isWhitespace = StringUtil.isWhitespace(tabCodePoint);

        assertTrue("Tab should be recognised as whitespace", isWhitespace);
    }
}
