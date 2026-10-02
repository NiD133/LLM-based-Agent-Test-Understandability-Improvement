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
public class NullReader_ESTest_test12 extends NullReader_ESTest_scaffolding {

    // A large skip value; the exact amount doesn't matter since skip() must throw before advancing
    private static final long LARGE_SKIP_AMOUNT = 2147483640L;

    @Test(timeout = 4000)
    public void test_skipAfterEofThrowsIOException() throws Throwable {
        // NullReader with default size 0: the very first read will reach EOF and set eof=true
        NullReader reader = new NullReader();
        char[] buffer = new char[4];

        // Exhaust the reader — size is 0 so read() returns -1 and marks EOF internally
        reader.read(buffer);

        // Attempting to skip after EOF must throw IOException("Skip after end of file")
        try {
            reader.skip(LARGE_SKIP_AMOUNT);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // Skip after end of file
            //
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
