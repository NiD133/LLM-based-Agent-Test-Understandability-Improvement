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
public class StringTokenizer_ESTest_test35 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that previousToken() returns null when the tokenizer has not
     * advanced forward yet (cursor is at the beginning of the token sequence).
     */
    @Test(timeout = 4000)
    public void test_previousToken_returnsNull_whenAtStartOfTokenSequence() throws Throwable {
        // CSV tokenizer for a string containing '*' characters alongside regular tokens
        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance("%*,d O%");

        // Configure '*' as an ignored character so it is stripped during tokenization
        csvTokenizer.setIgnoredChar('*');

        // No forward iteration has occurred, so there is no previous token
        String previousToken = csvTokenizer.previousToken();
        assertNull(previousToken);
    }
}
