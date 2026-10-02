package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test03 extends StringTokenizer_ESTest_scaffolding {

    /**
     * setTrimmerMatcher is a fluent setter: it should return the same
     * StringTokenizer instance so calls can be chained. Re-applying the
     * tokenizer's current trimmer matcher must therefore return that same
     * tokenizer.
     */
    @Test(timeout = 4000)
    public void setTrimmerMatcherReturnsSameTokenizerForChaining() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");
        StringMatcher currentTrimmer = tokenizer.getTrimmerMatcher();

        StringTokenizer returnedTokenizer = tokenizer.setTrimmerMatcher(currentTrimmer);

        assertSame(tokenizer, returnedTokenizer);
    }
}
