package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test11 extends NullReader_ESTest_scaffolding {

    /**
     * A NullReader constructed via the single-argument constructor should keep
     * the size it was given (even a negative one) and support marking. Closing
     * the shared singleton instance does not affect this separate reader's state.
     */
    @Test(timeout = 4000)
    public void constructorRetainsSizeAndSupportsMark() throws Throwable {
        final long emulatedSize = -827L;
        NullReader nullReader = new NullReader(emulatedSize);

        // Closing the shared singleton only resets the singleton's state.
        NullReader.INSTANCE.close();

        assertTrue("single-argument constructor enables mark support", nullReader.markSupported());
        assertEquals("getSize should return the size passed to the constructor",
                emulatedSize, nullReader.getSize());
    }
}
