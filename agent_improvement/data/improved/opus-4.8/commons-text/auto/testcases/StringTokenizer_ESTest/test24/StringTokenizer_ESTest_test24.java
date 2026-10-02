package org.apache.commons.text;

import static org.junit.Assert.assertTrue;

import org.apache.commons.text.matcher.StringMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test24 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A newly constructed StringTokenizer should ignore empty tokens by default,
     * even when built with null delimiter and quote matchers.
     */
    @Test(timeout = 4000)
    public void newTokenizerIgnoresEmptyTokensByDefault() throws Throwable {
        char[] input = new char[1];

        StringTokenizer tokenizer =
                new StringTokenizer(input, (StringMatcher) null, (StringMatcher) null);

        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }
}
