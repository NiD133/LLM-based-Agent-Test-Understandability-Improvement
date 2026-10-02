package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test10 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A tokenizer created with the no-argument constructor has no text to parse.
     * Requesting a token from it must not change the default configuration:
     * empty tokens are ignored by default.
     */
    @Test(timeout = 4000)
    public void nextTokenOnEmptyTokenizerLeavesIgnoreEmptyTokensAtDefault() throws Throwable {
        StringTokenizer emptyTokenizer = new StringTokenizer();

        emptyTokenizer.nextToken();

        assertTrue("ignoreEmptyTokens should default to true",
                emptyTokenizer.isIgnoreEmptyTokens());
    }
}
