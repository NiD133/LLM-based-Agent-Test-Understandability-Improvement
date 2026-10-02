package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test27 extends StringUtil_ESTest_scaffolding {

    /**
     * Code point 12 is the form-feed character ('\f'), which
     * {@link StringUtil#isActuallyWhitespace(int)} recognises as whitespace.
     */
    @Test(timeout = 4000)
    public void isActuallyWhitespace_returnsTrue_forFormFeedCodePoint() throws Throwable {
        int formFeedCodePoint = '\f'; // == 12

        boolean isWhitespace = StringUtil.isActuallyWhitespace(formFeedCodePoint);

        assertTrue(isWhitespace);
    }
}
