package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test14 extends NullReader_ESTest_scaffolding {

    /**
     * A NullReader created with the single-argument constructor should support
     * marking by default, and should report back the (negative) size it was
     * given without altering it.
     */
    @Test(timeout = 4000)
    public void markIsSupportedAndSizeIsReportedUnchanged() throws Throwable {
        final long emulatedSize = -827L;
        NullReader nullReader = new NullReader(emulatedSize);

        assertTrue("mark() should be supported by default", nullReader.markSupported());
        assertEquals("getSize() should return the size passed to the constructor",
                emulatedSize, nullReader.getSize());
    }
}
