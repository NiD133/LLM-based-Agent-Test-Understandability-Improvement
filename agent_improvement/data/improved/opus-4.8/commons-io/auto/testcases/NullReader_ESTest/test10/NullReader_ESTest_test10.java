package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test10 extends NullReader_ESTest_scaffolding {

    /**
     * A NullReader configured to throw an EOFException at end of file should do
     * so on a skip() call once its position has reached the emulated size.
     *
     * The reader is created with a negative size, so the first bulk read drives
     * the position up to (and clamped at) the size. A subsequent skip() then
     * detects end of file and throws.
     */
    @Test(timeout = 4000)
    public void skipAtEndOfFileThrowsEofException() throws Throwable {
        final long emulatedSize = -2742L;
        final boolean markSupported = true;
        final boolean throwEofException = true;
        NullReader reader = new NullReader(emulatedSize, markSupported, throwEofException);

        // Read into a 4-char buffer; this advances the position to the size.
        char[] buffer = new char[4];
        reader.read(buffer);

        // Now that end of file is reached, skipping must throw an EOFException.
        try {
            reader.skip(-2742);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            // The exception carries no message (getMessage() returns null).
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
