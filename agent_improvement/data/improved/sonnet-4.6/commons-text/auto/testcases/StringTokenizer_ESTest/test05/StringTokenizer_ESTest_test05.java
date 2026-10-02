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
public class StringTokenizer_ESTest_test05 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Tests that a TSV tokenizer starts at index 0 after setting a null ignored matcher.
     * Setting a null ignored matcher is a no-op for the iteration state, so nextIndex()
     * should still return 0 (the initial position before any tokens are consumed).
     */
    @Test(timeout = 4000)
    public void test_tsvTokenizer_nullIgnoredMatcher_nextIndexIsZero() throws Throwable {
        StringTokenizer tsvTokenizer = StringTokenizer.getTSVInstance();

        // Setting the ignored matcher to null returns the same tokenizer (fluent API)
        StringTokenizer tokenizerAfterSet = tsvTokenizer.setIgnoredMatcher((StringMatcher) null);

        // The iterator position should be at the start: index 0
        assertEquals(0, tokenizerAfterSet.nextIndex());
    }
}
