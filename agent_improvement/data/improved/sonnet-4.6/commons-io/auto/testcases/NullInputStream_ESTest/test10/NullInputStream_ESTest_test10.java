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
public class NullInputStream_ESTest_test10 extends NullInputStream_ESTest_scaffolding {

    /**
     * A NullInputStream with a negative size is effectively an empty/exhausted stream.
     * Reading into a zero-length buffer returns 0 (no bytes requested), available() returns 0
     * (negative size yields no available bytes), and mark is supported by default.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        NullInputStream streamWithNegativeSize = new NullInputStream(-30L);
        byte[] emptyBuffer = new byte[0];

        int bytesRead = streamWithNegativeSize.read(emptyBuffer);

        assertTrue(streamWithNegativeSize.markSupported());
        assertEquals(0, bytesRead);
        assertEquals(0, streamWithNegativeSize.available());
    }
}
