package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test19 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that calling previousToken() before any token has been read
     * returns null, since the cursor starts at the beginning and there is no
     * preceding token to move back to.
     */
    @Test(timeout = 4000)
    public void previousTokenAtStartReturnsNull() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");

        String tokenBeforeStart = tokenizer.previousToken();

        assertNull(tokenBeforeStart);
    }
}
