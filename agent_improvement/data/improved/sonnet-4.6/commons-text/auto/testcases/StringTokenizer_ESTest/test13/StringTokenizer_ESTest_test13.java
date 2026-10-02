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
public class StringTokenizer_ESTest_test13 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that after forEachRemaining consumes all tokens, nextIndex reflects
     * the position advanced past the single token produced by the given input and delimiters.
     */
    @Test(timeout = 4000)
    public void test_forEachRemaining_advancesNextIndex() throws Throwable {
        // "6j*-yw_2R-]" split by chars in "Am]OPZN#;`mL" yields one token ("6j*-yw_2R-")
        // because ']' is in the delimiter set, the trailing empty part is ignored
        String input = "6j*-yw_2R-]";
        String delimiterChars = "Am]OPZN#;`mL";
        StringTokenizer tokenizer = new StringTokenizer(input, delimiterChars);

        // Use a no-op consumer (mock) so forEachRemaining iterates through all remaining tokens
        Consumer<Object> noOpConsumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        tokenizer.forEachRemaining(noOpConsumer);

        // After consuming all tokens, nextIndex should be 1 (past the single token)
        assertEquals(1, tokenizer.nextIndex());
    }
}
