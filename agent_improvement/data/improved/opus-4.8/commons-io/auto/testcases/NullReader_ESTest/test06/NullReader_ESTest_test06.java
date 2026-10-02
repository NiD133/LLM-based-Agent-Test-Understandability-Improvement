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
public class NullReader_ESTest_test06 extends NullReader_ESTest_scaffolding {

    /**
     * The default NullReader emulates an empty reader (size 0). Skipping past
     * its end marks the reader as having reached end-of-file. Any subsequent
     * read must then fail with an IOException ("Read after end of file").
     */
    @Test(timeout = 4000)
    public void readAfterSkippingPastEndThrowsIOException() throws Throwable {
        NullReader emptyReader = new NullReader();
        char[] readBuffer = new char[3];

        // Skipping more than the emulated size drives the reader to end-of-file.
        emptyReader.skip(531L);

        try {
            emptyReader.read(readBuffer);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // Reading after the reader has reached end-of-file is not allowed.
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
