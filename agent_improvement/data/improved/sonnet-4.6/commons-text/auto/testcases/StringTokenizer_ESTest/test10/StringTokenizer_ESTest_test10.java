package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test10 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that calling nextToken() on an empty StringTokenizer (no input string)
     * returns null rather than throwing an exception, and that ignoreEmptyTokens is
     * enabled by default.
     */
    @Test(timeout = 4000)
    public void test10_nextTokenOnEmptyTokenizerReturnsNullAndIgnoreEmptyTokensIsDefaultTrue() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer();

        // nextToken() returns null when there are no tokens (unlike next() which would throw)
        tokenizer.nextToken();

        // ignoreEmptyTokens defaults to true
        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }
}
