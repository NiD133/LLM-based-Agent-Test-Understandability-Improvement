package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test26 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that a freshly created CSV tokenizer starts with nextIndex() == 0,
     * meaning the iterator cursor is positioned before the first token.
     */
    @Test(timeout = 4000)
    public void test_nextIndex_returnsZeroBeforeAnyIteration() throws Throwable {
        // A 7-element char array filled with '\0' (null chars) — valid CSV input
        char[] inputChars = new char[7];

        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance(inputChars);

        // Before any call to next(), the iterator index should be 0
        int nextIdx = csvTokenizer.nextIndex();
        assertEquals(0, nextIdx);
    }
}
