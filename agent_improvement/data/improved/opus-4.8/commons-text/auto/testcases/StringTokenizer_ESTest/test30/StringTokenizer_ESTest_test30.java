package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test30 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A StringTokenizer created with the no-argument constructor should ignore
     * empty tokens by default, even after its internal token array is accessed.
     */
    @Test(timeout = 4000)
    public void emptyTokenizerIgnoresEmptyTokensByDefault() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer();

        tokenizer.getTokenArray();

        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }
}
