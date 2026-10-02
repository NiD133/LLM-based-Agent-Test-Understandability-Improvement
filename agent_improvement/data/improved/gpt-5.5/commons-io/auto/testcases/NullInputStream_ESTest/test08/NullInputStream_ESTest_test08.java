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
public class NullInputStream_ESTest_test08 extends NullInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        final byte streamSize = 77;
        final int bufferLength = 3;

        final NullInputStream inputStream = new NullInputStream(streamSize);
        final byte[] buffer = new byte[bufferLength];

        final int bytesRead = inputStream.read(buffer);

        assertEquals(3L, inputStream.getPosition());
        assertEquals(3, bytesRead);
    }
}
