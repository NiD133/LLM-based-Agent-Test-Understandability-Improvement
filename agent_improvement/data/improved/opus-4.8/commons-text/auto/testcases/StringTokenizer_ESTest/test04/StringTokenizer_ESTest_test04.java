package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test04 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that {@link StringTokenizer#setTrimmerMatcher(StringMatcher)} supports a
     * fluent (method-chaining) style by returning the same tokenizer instance it was
     * called on, even when the trimmer is cleared by passing {@code null}.
     */
    @Test(timeout = 4000)
    public void setTrimmerMatcherToNullReturnsSameInstanceForChaining() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance();

        StringTokenizer returnedTokenizer = tokenizer.setTrimmerMatcher((StringMatcher) null);

        assertSame(tokenizer, returnedTokenizer);
    }
}
