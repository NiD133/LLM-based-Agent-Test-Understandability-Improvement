package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test3 extends BoundedReader_ESTest_scaffolding {

    /**
     * Skipping more characters than the underlying reader holds returns only the
     * number actually available, and a subsequent read() then reports EOF.
     */
    @Test(timeout = 4000)
    public void skipBeyondContentReturnsAvailableCountThenReadHitsEof() throws Throwable {
        // Underlying reader contains exactly 4 characters.
        StringReader underlying = new StringReader("@</'");
        int maxCharsFromTarget = 481;
        BoundedReader boundedReader = new BoundedReader(underlying, maxCharsFromTarget);

        boundedReader.reset();

        // Requesting to skip 481 chars only skips the 4 that actually exist.
        long skipped = boundedReader.skip(maxCharsFromTarget);
        assertEquals(4L, skipped);

        // skip() advanced the internal counter to maxCharsFromTarget, so reading
        // now yields EOF (-1).
        boundedReader.mark(1);
        int charRead = boundedReader.read();
        assertEquals(-1, charRead);
    }
}
