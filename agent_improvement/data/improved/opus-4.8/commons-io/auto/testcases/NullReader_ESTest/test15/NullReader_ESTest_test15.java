package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test15 extends NullReader_ESTest_scaffolding {

    /**
     * Verifies the initial state of a NullReader created with a (negative) emulated size:
     * the reader starts at position 0, reports the size it was constructed with,
     * and supports mark/reset by default.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        final long emulatedSize = -827L;
        NullReader nullReader = new NullReader(emulatedSize);

        long initialPosition = nullReader.getPosition();

        assertEquals("A freshly constructed reader should be at position 0", 0L, initialPosition);
        assertEquals("getSize should return the size passed to the constructor", emulatedSize, nullReader.getSize());
        assertTrue("The single-argument constructor should enable mark support", nullReader.markSupported());
    }
}
