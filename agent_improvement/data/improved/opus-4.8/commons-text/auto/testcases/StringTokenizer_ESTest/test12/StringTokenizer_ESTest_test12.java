package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test12 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that calling previousToken() before any token has been consumed
     * returns null, since there is no previous token to step back to.
     */
    @Test(timeout = 4000)
    public void previousTokenReturnsNullWhenNoTokensConsumedYet() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");
        tokenizer.setDelimiterChar(')');

        // Reuse the trimmer matcher as the quote matcher; configuration only,
        // tokenization has not been driven forward at this point.
        StringMatcher trimmerMatcher = tokenizer.getTrimmerMatcher();
        tokenizer.setQuoteMatcher(trimmerMatcher);

        String previous = tokenizer.previousToken();

        assertNull(previous);
    }
}
