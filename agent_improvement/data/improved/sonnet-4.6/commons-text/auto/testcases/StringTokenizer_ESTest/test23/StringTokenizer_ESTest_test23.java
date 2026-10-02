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
public class StringTokenizer_ESTest_test23 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that a newly created TSV tokenizer, after disabling empty-token skipping,
     * reports no previous position (previousIndex == -1) and correctly reflects the
     * updated ignoreEmptyTokens setting (false).
     */
    @Test(timeout = 4000)
    public void test_tsvTokenizer_setIgnoreEmptyTokensFalse_initialStateIsCorrect() throws Throwable {
        // Create a TSV tokenizer; setIgnoreEmptyTokens returns 'this', so one variable suffices.
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted")
                .setIgnoreEmptyTokens(false);

        // A freshly created tokenizer has not advanced yet, so previousIndex must be -1.
        assertEquals(-1, tokenizer.previousIndex());

        // The ignoreEmptyTokens flag must reflect the value we just set.
        assertFalse(tokenizer.isIgnoreEmptyTokens());
    }
}
