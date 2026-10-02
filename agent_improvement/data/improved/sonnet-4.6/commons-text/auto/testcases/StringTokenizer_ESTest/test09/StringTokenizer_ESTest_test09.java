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
public class StringTokenizer_ESTest_test09 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that after advancing forward with nextToken(), previousToken() correctly
     * returns the tokenizer to the previous position and retrieves the single CSV token.
     *
     * The input "%*dR|" contains no commas, so the CSV tokenizer treats the entire
     * string as one token. After nextToken() consumes it, nextIndex() reflects
     * the cursor has moved past position 0. Calling previousToken() moves the
     * cursor back and returns the token that was just passed.
     */
    @Test(timeout = 4000)
    public void test09_previousTokenReturnsLastConsumedToken() throws Throwable {
        // A single-token CSV string (no commas, so the whole value is one token)
        StringTokenizer tokenizer = StringTokenizer.getCSVInstance("%*dR|");

        // Advance past the first (and only) token
        tokenizer.nextToken();

        // Cursor is now at index 1, meaning one token has been consumed
        assertEquals(1, tokenizer.nextIndex());

        // Navigate backwards: previousToken() moves the cursor back and returns the token
        String previousToken = tokenizer.previousToken();
        assertEquals("%*dR|", previousToken);
    }
}
