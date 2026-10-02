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
public class NullReader_ESTest_test08 extends NullReader_ESTest_scaffolding {

    /**
     * A zero-length NullReader immediately reaches EOF on the first read().
     * Any subsequent read() must throw IOException because the reader is
     * already in the EOF state ("Read after end of file").
     */
    @Test(timeout = 4000)
    public void test_readAfterEof_throwsIOException() throws Throwable {
        // A default NullReader has size 0, so the very first read() signals EOF
        // and sets the internal eof flag to true.
        NullReader nullReader = new NullReader();
        nullReader.read(); // advances past end-of-file, marking eof = true

        // A second read() while eof is true must throw IOException.
        try {
            nullReader.read();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // Read after end of file
            //
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
