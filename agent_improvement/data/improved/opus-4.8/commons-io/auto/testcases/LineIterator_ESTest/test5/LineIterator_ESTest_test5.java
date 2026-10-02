package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LineIterator_ESTest_test5 extends LineIterator_ESTest_scaffolding {

    /**
     * Verifies that {@link LineIterator#remove()} is unsupported: calling it
     * always throws an {@link UnsupportedOperationException}, regardless of the
     * reader's contents.
     */
    @Test(timeout = 4000)
    public void removeShouldThrowUnsupportedOperationException() throws Throwable {
        StringReader reader = new StringReader("org.apache.commons.io.LineIterator");
        LineIterator lineIterator = new LineIterator(reader);

        try {
            lineIterator.remove();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // LineIterator.remove() rejects the call with "remove not supported".
            verifyException("org.apache.commons.io.LineIterator", e);
        }
    }
}
