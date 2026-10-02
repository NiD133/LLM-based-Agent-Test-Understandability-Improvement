package org.apache.commons.text;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test17 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that for a single-token TSV input, stepping forward with next()
     * and then back with previous() returns that same token, and that the
     * cursor starts before the first token (previousIndex() == -1).
     */
    @Test(timeout = 4000)
    public void testPreviousReturnsSingleTokenAfterNext() throws Throwable {
        // A TSV input with no tab separators parses as a single token.
        String singleToken = ",OF0)2fR[p0$";
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance(singleToken);

        // Before any iteration the cursor sits before the first token.
        assertEquals(-1, tokenizer.previousIndex());

        // Advance past the only token, then step back onto it.
        tokenizer.next();
        String previousToken = tokenizer.previous();

        assertEquals(singleToken, previousToken);
    }
}
