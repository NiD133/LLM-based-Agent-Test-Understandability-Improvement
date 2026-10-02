package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test33 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A freshly created tokenizer starts positioned before its first token, so
     * there is no previous token to step back to. In that situation
     * {@link StringTokenizer#previousToken()} should return {@code null} rather
     * than throwing.
     */
    @Test(timeout = 4000)
    public void previousTokenAtStartReturnsNull() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance(" \t\n\r\f");

        String previousToken = tokenizer.previousToken();

        assertNull(previousToken);
    }
}
