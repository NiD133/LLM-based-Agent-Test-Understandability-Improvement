package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test00 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A freshly constructed tokenizer that has not been iterated should:
     *   - report its default {@code ignoreEmptyTokens} setting as true, and
     *   - render a "not tokenized yet" string from {@link StringTokenizer#toString()}.
     */
    @Test(timeout = 4000)
    public void newTokenizer_reportsDefaultsAndNotTokenizedYet() throws Throwable {
        char[] input = new char[1];
        StringMatcher delimiterMatcher = mock(StringMatcher.class, new ViolatedAssumptionAnswer());

        StringTokenizer tokenizer = new StringTokenizer(input, delimiterMatcher);
        String description = tokenizer.toString();

        assertTrue("ignoreEmptyTokens should default to true", tokenizer.isIgnoreEmptyTokens());
        assertEquals("StringTokenizer[not tokenized yet]", description);
    }
}
