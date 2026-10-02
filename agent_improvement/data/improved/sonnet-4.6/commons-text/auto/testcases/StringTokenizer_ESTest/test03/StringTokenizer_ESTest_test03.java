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
public class StringTokenizer_ESTest_test03 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that setTrimmerMatcher() returns the same StringTokenizer instance,
     * enabling fluent/builder-style method chaining.
     */
    @Test(timeout = 4000)
    public void test_setTrimmerMatcher_returnsSameInstance() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");

        // Retrieve the current trimmer matcher and re-apply it
        StringMatcher currentTrimmer = tokenizer.getTrimmerMatcher();
        StringTokenizer returnedTokenizer = tokenizer.setTrimmerMatcher(currentTrimmer);

        // setTrimmerMatcher must return 'this' to support method chaining
        assertSame(tokenizer, returnedTokenizer);
    }
}
