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
public class NullInputStream_ESTest_test04 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that skipping 0 bytes on the shared INSTANCE (which has size=0 and is
     * therefore already at EOF) returns -1, while a separately constructed 1-byte stream
     * remains unaffected: it still reports 1 byte available and does not support mark.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // A 1-byte stream with mark disabled and no EOFException on end-of-stream
        NullInputStream oneByteStream = new NullInputStream(1L, false, false);

        // INSTANCE is the shared singleton with size=0; at construction its position==size,
        // so skipping 0 bytes still hits EOF and returns -1 (throwEofException is false on INSTANCE)
        long skipResultOnInstance = oneByteStream.INSTANCE.skip(0L);

        // oneByteStream was never read, so all 1 byte is still available
        assertEquals(1, oneByteStream.available());

        // oneByteStream was constructed with markSupported=false
        assertFalse(oneByteStream.markSupported());

        // skip() on the already-exhausted INSTANCE returns EOF (-1)
        assertEquals((-1L), skipResultOnInstance);
    }
}
