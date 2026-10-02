package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test34 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that a newly constructed StringTokenizer ignores empty tokens
     * by default, regardless of the delimiter and quote characters supplied.
     */
    @Test(timeout = 4000)
    public void newTokenizerIgnoresEmptyTokensByDefault() throws Throwable {
        final char delimiterChar = '6';
        final char quoteChar = '6';
        StringTokenizer tokenizer =
            new StringTokenizer("J)u`2Cx\"(DZ_nO'", delimiterChar, quoteChar);

        assertTrue(
            "A freshly created tokenizer should ignore empty tokens by default",
            tokenizer.isIgnoreEmptyTokens());
    }
}
