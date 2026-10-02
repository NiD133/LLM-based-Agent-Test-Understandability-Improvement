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
public class NullReader_ESTest_test12 extends NullReader_ESTest_scaffolding {

    /**
     * A default {@link NullReader} emulates a reader of size 0, so the very first
     * read immediately reaches the end of file. Once EOF has been reached, calling
     * {@link NullReader#skip(long)} must fail with an {@link IOException}.
     */
    @Test(timeout = 4000)
    public void skipAfterEndOfFileThrowsIOException() throws Throwable {
        // A default NullReader has size 0, so it is already at end of file.
        NullReader nullReader = new NullReader();

        // First read consumes the (empty) content and marks the reader as EOF.
        char[] buffer = new char[4];
        nullReader.read(buffer);

        // Skipping after EOF is not allowed and must raise an IOException.
        try {
            nullReader.skip(2147483640L);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // Expected: "Skip after end of file"
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
