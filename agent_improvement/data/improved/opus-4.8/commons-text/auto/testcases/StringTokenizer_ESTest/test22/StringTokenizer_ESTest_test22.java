package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test22 extends StringTokenizer_ESTest_scaffolding {

    /**
     * StringTokenizer implements ListIterator but does not support mutation.
     * Calling remove() must therefore always throw UnsupportedOperationException.
     */
    @Test(timeout = 4000)
    public void removeThrowsUnsupportedOperationException() throws Throwable {
        StringTokenizer tsvTokenizer = StringTokenizer.getTSVInstance();

        try {
            tsvTokenizer.remove();
            fail("remove() should not be supported and must throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // remove() is unsupported by StringTokenizer
            verifyException("org.apache.commons.text.StringTokenizer", expected);
        }
    }
}
