package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test05 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A null ignored matcher is silently rejected by {@link StringTokenizer#setIgnoredMatcher(StringMatcher)},
     * which returns the tokenizer itself for fluent chaining. A freshly created tokenizer has not yet
     * advanced through any tokens, so its next-token index starts at zero.
     */
    @Test(timeout = 4000)
    public void setIgnoredMatcherWithNullKeepsTokenizerAtStart() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance();

        StringTokenizer returnedTokenizer = tokenizer.setIgnoredMatcher((StringMatcher) null);

        assertEquals(0, returnedTokenizer.nextIndex());
    }
}
