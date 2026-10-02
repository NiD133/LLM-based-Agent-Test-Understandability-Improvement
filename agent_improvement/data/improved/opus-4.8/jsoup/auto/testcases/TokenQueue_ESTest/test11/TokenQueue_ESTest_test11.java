package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test11 extends TokenQueue_ESTest_scaffolding {

    /**
     * A lone "-" identifier is a special case in CSS serialization: a hyphen as the
     * first and only character must be backslash-escaped, yielding "\-".
     */
    @Test(timeout = 4000)
    public void escapeCssIdentifier_loneHyphen_isBackslashEscaped() throws Throwable {
        String escaped = TokenQueue.escapeCssIdentifier("-");

        assertEquals("\\-", escaped);
    }
}
