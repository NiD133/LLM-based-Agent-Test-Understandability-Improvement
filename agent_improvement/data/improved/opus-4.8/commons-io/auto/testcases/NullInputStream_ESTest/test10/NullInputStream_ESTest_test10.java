package org.apache.commons.io.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test10 extends NullInputStream_ESTest_scaffolding {

    /**
     * Reading into an empty byte array should always return 0 (no bytes requested),
     * regardless of the emulated stream size. The single-argument constructor also
     * enables mark support by default and reports no bytes available here.
     */
    @Test(timeout = 4000)
    public void readIntoEmptyArrayReturnsZero() throws Throwable {
        NullInputStream nullInputStream = new NullInputStream(-30L);

        byte[] emptyBuffer = new byte[0];
        int bytesRead = nullInputStream.read(emptyBuffer);

        assertEquals("Reading into an empty array should report zero bytes read", 0, bytesRead);
        assertTrue("Mark support is enabled by the single-argument constructor", nullInputStream.markSupported());
        assertEquals("A non-positive emulated size leaves no bytes available", 0, nullInputStream.available());
    }
}
