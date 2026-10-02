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
public class StringTokenizer_ESTest_test34 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that the default value of ignoreEmptyTokens is true when constructing
     * a StringTokenizer with the same character used as both delimiter and quote.
     */
    @Test(timeout = 4000)
    public void test34_ignoreEmptyTokensDefaultsTrueWhenDelimiterEqualsQuoteChar() throws Throwable {
        // Use '6' as both the delimiter and the quote character
        char delimiterAndQuoteChar = '6';
        StringTokenizer tokenizer = new StringTokenizer("J)u`2Cx\"(DZ_nO'", delimiterAndQuoteChar, delimiterAndQuoteChar);

        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }
}
