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
public class StringTokenizer_ESTest_test07 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that previousToken() returns null when the tokenizer cursor is at
     * the start (i.e. no token has been consumed yet, so there is no previous token).
     *
     * The input char array contains ')' characters (the quote char) at a few positions.
     * '(' is the delimiter and ')' is the quote character.
     * Regardless of the content, calling previousToken() before any forward iteration
     * must always yield null.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Build an input char array with ')' (the quote char) at positions 0, 1, and 4;
        // the remaining positions are the null char '\0'.
        char[] inputChars = new char[9];
        inputChars[0] = ')';
        inputChars[1] = ')';
        inputChars[4] = ')';

        // Create a tokenizer: '(' is the delimiter, ')' is the quote character.
        StringTokenizer tokenizer = new StringTokenizer(inputChars, '(', ')');

        // The cursor starts before the first token, so there is no previous token.
        String previousToken = tokenizer.previousToken();
        assertNull(previousToken);
    }
}
