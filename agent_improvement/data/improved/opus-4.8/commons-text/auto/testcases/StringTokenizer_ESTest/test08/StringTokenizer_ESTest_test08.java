package org.apache.commons.text;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.util.NoSuchElementException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test08 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A freshly created tokenizer is positioned before the first token, so there is no
     * previous token to move back to. Calling {@link StringTokenizer#previous()} in this
     * state must throw a {@link NoSuchElementException}.
     */
    @Test(timeout = 4000)
    public void previousOnFreshTokenizerThrowsNoSuchElementException() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer("add() is unsupported");

        try {
            tokenizer.previous();
            fail("Expected NoSuchElementException because there is no previous token");
        } catch (NoSuchElementException e) {
            // Thrown by StringTokenizer.previous() with no message.
            verifyException("org.apache.commons.text.StringTokenizer", e);
        }
    }
}
