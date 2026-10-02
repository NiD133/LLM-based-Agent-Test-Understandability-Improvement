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
public class NullReader_ESTest_test00 extends NullReader_ESTest_scaffolding {

    /**
     * Skipping zero characters on a freshly created reader should skip nothing
     * (return 0) and leave the reader's mark support unchanged (enabled by the
     * single-argument constructor).
     */
    @Test(timeout = 4000)
    public void skipZeroCharactersReturnsZeroAndKeepsMarkSupported() throws Throwable {
        final long emulatedSize = 1017L;
        NullReader reader = new NullReader(emulatedSize);

        long charactersSkipped = reader.skip(0L);

        assertEquals("Skipping 0 characters should skip nothing", 0L, charactersSkipped);
        assertTrue("Single-arg constructor enables mark support", reader.markSupported());
    }
}
