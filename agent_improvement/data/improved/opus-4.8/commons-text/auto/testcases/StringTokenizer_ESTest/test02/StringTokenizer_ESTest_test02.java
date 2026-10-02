package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test02 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A TSV tokenizer built from an empty string yields no tokens, so the
     * iterator starts at position zero with nothing behind it. Calling
     * previousToken() in this state must return null rather than throw.
     */
    @Test(timeout = 4000)
    public void previousTokenOnEmptyInputReturnsNull() throws Throwable {
        StringTokenizer emptyTsvTokenizer = StringTokenizer.getTSVInstance("");

        String previousToken = emptyTsvTokenizer.previousToken();

        assertNull(previousToken);
    }
}
