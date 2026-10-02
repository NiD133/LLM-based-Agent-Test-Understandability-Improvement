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
public class StringTokenizer_ESTest_test02 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that calling previousToken() on a TSV tokenizer initialized with an empty string
     * returns null, because there are no tokens and the iterator starts at position 0 (no previous element).
     */
    @Test(timeout = 4000)
    public void test02_previousTokenOnEmptyTsvInputReturnsNull() throws Throwable {
        StringTokenizer tsvTokenizer = StringTokenizer.getTSVInstance("");
        String previousToken = tsvTokenizer.previousToken();
        assertNull(previousToken);
    }
}
