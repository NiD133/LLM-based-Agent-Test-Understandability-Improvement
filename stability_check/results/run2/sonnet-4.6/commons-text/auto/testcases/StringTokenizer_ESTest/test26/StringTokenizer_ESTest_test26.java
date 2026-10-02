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
public class StringTokenizer_ESTest_test26 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that a freshly created CSV tokenizer reports nextIndex() == 0,
     * meaning the iterator position starts at the beginning before any tokens
     * have been consumed.
     */
    @Test(timeout = 4000)
    public void test26_nextIndexIsZeroAtStartOfIteration() throws Throwable {
        // A char array of 7 null characters serves as the tokenizer input
        char[] inputChars = new char[7];
        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance(inputChars);

        // Before advancing the iterator, nextIndex() must return 0
        int nextIdx = csvTokenizer.nextIndex();

        assertEquals("nextIndex() should be 0 before any token has been consumed", 0, nextIdx);
    }
}
