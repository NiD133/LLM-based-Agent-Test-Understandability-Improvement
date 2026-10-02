package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test28 extends StringTokenizer_ESTest_scaffolding {

    /**
     * StringTokenizer implements ListIterator, but it is read-only: the optional
     * {@code set(String)} operation is not supported and must always throw
     * UnsupportedOperationException, regardless of the argument passed.
     */
    @Test(timeout = 4000)
    public void setAlwaysThrowsUnsupportedOperationException() throws Throwable {
        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance();

        try {
            csvTokenizer.set(" ");
            fail("set() should not be supported and must throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Expected: set() is unsupported
            verifyException("org.apache.commons.text.StringTokenizer", e);
        }
    }
}
