package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test19 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that {@link NullInputStream#init()} returns an instance whose
     * initial state matches its construction: it is open, positioned at the
     * start, supports marking, and reports the size given to the constructor
     * (even when that size is negative).
     */
    @Test(timeout = 4000)
    public void initReturnsStreamWithExpectedInitialState() throws Throwable {
        final long emulatedSize = -30L;
        NullInputStream stream = new NullInputStream(emulatedSize);

        NullInputStream initializedStream = stream.init();

        assertFalse("stream should be open after init()", initializedStream.isClosed());
        assertEquals("size should match the constructor argument", emulatedSize, initializedStream.getSize());
        assertTrue("marking is supported by default", initializedStream.markSupported());
        assertEquals("position should be reset to the start", 0L, initializedStream.getPosition());
    }
}
