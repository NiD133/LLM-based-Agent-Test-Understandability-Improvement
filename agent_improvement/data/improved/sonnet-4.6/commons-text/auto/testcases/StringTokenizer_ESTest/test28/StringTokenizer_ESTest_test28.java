package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test28 extends StringTokenizer_ESTest_scaffolding {

    /**
     * Verifies that calling {@code set()} on a CSV StringTokenizer instance
     * throws {@link UnsupportedOperationException}, since the iterator's
     * {@code set} operation is not supported.
     */
    @Test(timeout = 4000)
    public void test_setOnCSVInstance_throwsUnsupportedOperationException() throws Throwable {
        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance();

        try {
            csvTokenizer.set(" ");
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            verifyException("org.apache.commons.text.StringTokenizer", e);
        }
    }
}
