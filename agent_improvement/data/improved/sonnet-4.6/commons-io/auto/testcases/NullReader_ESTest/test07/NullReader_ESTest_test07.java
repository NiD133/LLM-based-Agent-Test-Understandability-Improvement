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
public class NullReader_ESTest_test07 extends NullReader_ESTest_scaffolding {

    /**
     * Reading into an empty char array should return 0 (no characters consumed),
     * and the default NullReader should support mark/reset.
     */
    @Test(timeout = 4000)
    public void testReadWithEmptyBufferReturnsZeroAndDefaultReaderSupportsMarkReset() throws Throwable {
        NullReader reader = new NullReader();
        char[] emptyBuffer = new char[0];

        int charsRead = reader.read(emptyBuffer);

        assertEquals(0, charsRead);
        assertTrue(reader.markSupported());
    }
}
