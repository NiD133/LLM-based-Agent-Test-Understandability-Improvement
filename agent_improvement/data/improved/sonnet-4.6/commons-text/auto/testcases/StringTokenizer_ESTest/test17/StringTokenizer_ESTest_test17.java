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
public class StringTokenizer_ESTest_test17 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Tests that calling previous() after next() returns the same token.
     *
     * A TSV tokenizer with no tab characters treats the entire input as a single token.
     * After advancing forward with next() and then going back with previous(),
     * the returned token should be the original input string.
     * The initial previousIndex() should be -1 (before any element).
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        String input = ",OF0)2fR[p0$";
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance(input);

        // Before any iteration, previousIndex() returns -1 (cursor is before the first element)
        assertEquals(-1, tokenizer.previousIndex());

        // Advance forward past the first (and only) token
        tokenizer.next();

        // Going back one step should return the single token, which equals the entire input
        String previousToken = tokenizer.previous();
        assertEquals(input, previousToken);
    }
}
