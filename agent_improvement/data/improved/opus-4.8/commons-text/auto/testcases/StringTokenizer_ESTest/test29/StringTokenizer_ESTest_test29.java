package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test29 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Enabling "empty token as null" should be remembered by the tokenizer, and
     * calling previousToken() before any token has been read must not disturb
     * that flag (it simply has no previous token to return).
     */
    @Test(timeout = 4000)
    public void test29() throws Throwable {
        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance(";A<w1:!}k:j8%,");

        // setEmptyTokenAsNull returns the same tokenizer for fluent chaining.
        StringTokenizer sameTokenizer = csvTokenizer.setEmptyTokenAsNull(true);

        // No token has been consumed yet, so there is no previous token.
        sameTokenizer.previousToken();

        assertTrue(csvTokenizer.isEmptyTokenAsNull());
    }
}
