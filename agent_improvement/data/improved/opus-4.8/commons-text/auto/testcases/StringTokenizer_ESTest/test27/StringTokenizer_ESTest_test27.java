package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test27 extends StringTokenizer_ESTest_scaffolding {

    /**
     * StringTokenizer implements ListIterator but does not support mutation:
     * calling add(...) must always throw UnsupportedOperationException.
     */
    @Test(timeout = 4000)
    public void addAlwaysThrowsUnsupportedOperationException() throws Throwable {
        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance();

        try {
            csvTokenizer.add("null");
            fail("add() should throw UnsupportedOperationException because the tokenizer is read-only");
        } catch (UnsupportedOperationException expected) {
            // add() is unsupported on StringTokenizer
            verifyException("org.apache.commons.text.StringTokenizer", expected);
        }
    }
}
