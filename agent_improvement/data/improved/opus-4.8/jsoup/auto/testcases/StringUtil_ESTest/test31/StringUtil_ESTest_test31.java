package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test31 extends StringUtil_ESTest_scaffolding {

    /**
     * The HTML-spec whitespace set includes the form feed character, whose code point is 12 ('\f').
     * isWhitespace should therefore report true for it.
     */
    @Test(timeout = 4000)
    public void isWhitespace_returnsTrue_forFormFeedCodePoint() throws Throwable {
        int formFeedCodePoint = '\f'; // 12

        boolean isWhitespace = StringUtil.isWhitespace(formFeedCodePoint);

        assertTrue(isWhitespace);
    }
}
