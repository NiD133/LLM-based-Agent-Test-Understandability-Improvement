package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test09 extends TokenQueue_ESTest_scaffolding {

    /**
     * escapeCssIdentifier should backslash-escape characters that are not valid
     * in a CSS selector. Here the two '[' characters are escaped, while the
     * leading '-', the letters, and the digit are left untouched.
     */
    @Test(timeout = 4000)
    public void escapeCssIdentifierEscapesSquareBrackets() throws Throwable {
        String escaped = TokenQueue.escapeCssIdentifier("-[-Tg0Y[E");

        assertEquals("-\\[-Tg0Y\\[E", escaped);
    }
}
