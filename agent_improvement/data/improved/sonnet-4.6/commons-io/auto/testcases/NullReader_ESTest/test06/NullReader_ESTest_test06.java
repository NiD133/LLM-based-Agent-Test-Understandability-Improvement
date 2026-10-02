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
public class NullReader_ESTest_test06 extends NullReader_ESTest_scaffolding {

    /**
     * Verifies that reading into a char array after skipping past EOF throws IOException.
     *
     * NullReader() creates a size-0 reader (no content). Calling skip() on it
     * immediately reaches EOF and sets the internal eof flag. Any subsequent
     * read attempt must throw IOException with "Read after end of file".
     */
    @Test(timeout = 4000)
    public void test_readAfterSkipPastEndOfFile_throwsIOException() throws Throwable {
        NullReader sizeZeroReader = new NullReader();
        char[] buffer = new char[3];

        // Skipping any amount on a size-0 reader reaches EOF immediately
        sizeZeroReader.skip(531L);

        // Subsequent read must reject the call because EOF was already signalled
        try {
            sizeZeroReader.read(buffer);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // Read after end of file
            //
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
