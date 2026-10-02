package org.apache.commons.io.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test13 extends NullInputStream_ESTest_scaffolding {

    /**
     * On a freshly constructed stream, available() should report the full
     * emulated size, and mark() should be supported by default.
     */
    @Test(timeout = 4000)
    public void availableReturnsFullSizeAndMarkIsSupported() throws Throwable {
        final long emulatedSize = 2480L;
        NullInputStream stream = new NullInputStream(emulatedSize);

        int bytesAvailable = stream.available();

        assertEquals("All bytes should be available before any read", 2480, bytesAvailable);
        assertTrue("mark() is supported by default", stream.markSupported());
    }
}
