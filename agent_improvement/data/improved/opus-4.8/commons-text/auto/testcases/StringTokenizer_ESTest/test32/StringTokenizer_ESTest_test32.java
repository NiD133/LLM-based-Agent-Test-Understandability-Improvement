package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test32 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A newly constructed tokenizer should ignore empty tokens by default,
     * even when built from a null character array.
     */
    @Test(timeout = 4000)
    public void newTokenizerIgnoresEmptyTokensByDefault() throws Throwable {
        char[] nullInput = null;
        String delimiter = "StringTokenizer";

        StringTokenizer tokenizer = new StringTokenizer(nullInput, delimiter);

        assertTrue("Empty tokens should be ignored by default",
                tokenizer.isIgnoreEmptyTokens());
    }
}
