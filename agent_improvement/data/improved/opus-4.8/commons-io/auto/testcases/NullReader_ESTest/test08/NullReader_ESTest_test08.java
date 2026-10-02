package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test08 extends NullReader_ESTest_scaffolding {

    /**
     * The default {@link NullReader} emulates a size-0 reader that does not throw
     * an {@link java.io.EOFException}. The first {@code read()} therefore returns
     * EOF and marks the reader as ended; a subsequent {@code read()} must fail
     * with an {@link IOException} ("Read after end of file").
     */
    @Test(timeout = 4000)
    public void readingPastEndOfFileThrowsIOException() throws Throwable {
        NullReader nullReader = new NullReader();

        // First read reaches the end of the (size 0) reader.
        nullReader.read();

        // Reading again, after the end has been reached, is not allowed.
        try {
            nullReader.read();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // Thrown by NullReader with the message "Read after end of file".
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
