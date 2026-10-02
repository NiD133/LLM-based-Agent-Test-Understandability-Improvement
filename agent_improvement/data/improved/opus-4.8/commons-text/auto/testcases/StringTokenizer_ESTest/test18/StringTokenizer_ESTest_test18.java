package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test18 extends StringTokenizer_ESTest_scaffolding {

    /**
     * When the delimiter string is identical to the entire input, the whole
     * input is consumed as a single delimiter. This yields only empty tokens,
     * which are ignored by default, so the tokenizer reports a size of zero.
     */
    @Test(timeout = 4000)
    public void delimiterEqualToWholeInputProducesNoTokens() throws Throwable {
        final String input = "z!S]_Cf!Rm6c";
        StringTokenizer tokenizer = new StringTokenizer(input, 'x');

        tokenizer.setDelimiterString(input);

        assertEquals(0, tokenizer.size());
    }
}
