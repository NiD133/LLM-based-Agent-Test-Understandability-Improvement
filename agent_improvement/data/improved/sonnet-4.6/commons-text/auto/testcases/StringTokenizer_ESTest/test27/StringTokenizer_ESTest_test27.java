package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test27 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that calling add() on a CSV StringTokenizer throws UnsupportedOperationException,
     * because StringTokenizer does not support structural modification via the ListIterator interface.
     */
    @Test(timeout = 4000)
    public void test_addOnCsvTokenizer_throwsUnsupportedOperationException() throws Throwable {
        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance();

        try {
            csvTokenizer.add("null");
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            verifyException("org.apache.commons.text.StringTokenizer", e);
        }
    }
}
