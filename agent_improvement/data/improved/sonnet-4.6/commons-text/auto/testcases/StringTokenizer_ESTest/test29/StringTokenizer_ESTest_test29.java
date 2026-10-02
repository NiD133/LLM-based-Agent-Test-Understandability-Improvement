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
public class StringTokenizer_ESTest_test29 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that setEmptyTokenAsNull uses a fluent API (returns the same tokenizer instance),
     * that previousToken() can be called at the initial position without error,
     * and that the emptyTokenAsNull flag is correctly reflected by isEmptyTokenAsNull().
     */
    @Test(timeout = 4000)
    public void test_setEmptyTokenAsNull_returnsFluentInstance_andFlagIsReflectedByGetter() throws Throwable {
        // Create a CSV tokenizer with a string containing mixed delimiters and special characters
        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance(";A<w1:!}k:j8%,");

        // setEmptyTokenAsNull returns 'this' (fluent API), so fluentTokenizer is the same instance
        StringTokenizer fluentTokenizer = csvTokenizer.setEmptyTokenAsNull(true);

        // Call previousToken() at the initial position (before any tokens have been iterated)
        fluentTokenizer.previousToken();

        // The emptyTokenAsNull flag should be true on the original tokenizer,
        // confirming that setEmptyTokenAsNull mutated the shared instance
        assertTrue(csvTokenizer.isEmptyTokenAsNull());
    }
}
