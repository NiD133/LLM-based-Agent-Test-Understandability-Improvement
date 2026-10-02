package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test10 extends NullReader_ESTest_scaffolding {

    // A negative size causes the reader to reach EOF immediately once position is adjusted to match size.
    private static final long NEGATIVE_SIZE = -2742L;

    @Test(timeout = 4000)
    public void test10_skipAtEofThrowsEOFExceptionWhenConfigured() throws Throwable {
        // Create a NullReader with a negative size, mark support enabled, and EOF exception throwing enabled.
        // With a negative size the initial read() call drives position to size, leaving the reader at EOF.
        NullReader readerWithNegativeSize = new NullReader(NEGATIVE_SIZE, true, true);

        // Perform an initial read to advance position to the negative size boundary (EOF).
        char[] buffer = new char[4];
        readerWithNegativeSize.read(buffer);

        // Attempting to skip after reaching EOF must throw EOFException because throwEofException=true.
        try {
            readerWithNegativeSize.skip((long) NEGATIVE_SIZE);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
