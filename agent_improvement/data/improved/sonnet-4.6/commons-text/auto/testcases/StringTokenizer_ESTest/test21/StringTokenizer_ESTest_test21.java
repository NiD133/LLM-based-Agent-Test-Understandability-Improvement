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
public class StringTokenizer_ESTest_test21 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A newly created TSV tokenizer with null input has no tokens to iterate over.
     * The iterator starts before the first element, so previousIndex() must return -1,
     * indicating there is no valid previous position.
     */
    @Test(timeout = 4000)
    public void test_newTsvTokenizerWithNullInput_previousIndexIsMinusOne() throws Throwable {
        StringTokenizer tsvTokenizer = StringTokenizer.getTSVInstance((char[]) null);
        assertEquals(-1, tsvTokenizer.previousIndex());
    }
}
