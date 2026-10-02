package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test31 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A newly constructed StringTokenizer should ignore empty tokens by default,
     * even when null delimiter and quote matchers are supplied.
     */
    @Test(timeout = 4000)
    public void newTokenizerIgnoresEmptyTokensByDefault() throws Throwable {
        StringMatcher noDelimiterMatcher = null;
        StringMatcher noQuoteMatcher = null;
        StringTokenizer tokenizer =
                new StringTokenizer("1EDH+{PcZzyE", noDelimiterMatcher, noQuoteMatcher);

        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }
}
