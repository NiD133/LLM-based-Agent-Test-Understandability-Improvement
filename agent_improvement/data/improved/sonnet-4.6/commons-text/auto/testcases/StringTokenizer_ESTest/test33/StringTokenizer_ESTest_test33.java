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
public class StringTokenizer_ESTest_test33 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that calling previousToken() on a freshly created TSV tokenizer
     * (with the cursor before all tokens) returns null, since there is no
     * previous token to navigate to.
     */
    @Test(timeout = 4000)
    public void test33_previousTokenReturnsNullWhenAtStart() throws Throwable {
        // Create a TSV tokenizer positioned before the first token
        StringTokenizer tsvTokenizer = StringTokenizer.getTSVInstance(" \t\n\r\f");

        // Navigating backward at the start of iteration should yield null
        String previousToken = tsvTokenizer.previousToken();

        assertNull(previousToken);
    }
}
