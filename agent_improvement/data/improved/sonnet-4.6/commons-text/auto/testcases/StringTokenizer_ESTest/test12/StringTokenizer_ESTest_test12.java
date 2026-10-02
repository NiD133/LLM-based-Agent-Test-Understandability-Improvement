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
public class StringTokenizer_ESTest_test12 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that previousToken() returns null when the tokenizer has not yet
     * advanced past any token (i.e., the iterator is at the beginning).
     */
    @Test(timeout = 4000)
    public void test_previousToken_returnsNull_whenNoTokenHasBeenConsumed() throws Throwable {
        // Create a TSV tokenizer and reconfigure its delimiter and quote matcher
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");
        tokenizer.setDelimiterChar(')');
        StringMatcher trimmerMatcher = tokenizer.getTrimmerMatcher();
        tokenizer.setQuoteMatcher(trimmerMatcher);

        // At the start of iteration there is no previous token, so null is expected
        String previousToken = tokenizer.previousToken();
        assertNull(previousToken);
    }
}
