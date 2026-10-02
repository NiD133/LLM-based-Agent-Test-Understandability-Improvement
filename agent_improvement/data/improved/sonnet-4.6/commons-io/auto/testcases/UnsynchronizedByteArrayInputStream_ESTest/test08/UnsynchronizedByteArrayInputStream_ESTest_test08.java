package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test08 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    // When offset equals the array length, the stream starts at end-of-stream:
    // available() should be 0 and read() should return -1 (END_OF_STREAM).
    @Test(timeout = 4000)
    public void test08_offsetAtEndOfArray_streamIsImmediatelyExhausted() throws Throwable {
        byte[] singleByteArray = new byte[1];
        int offsetAtEnd = 1; // offset == array length, so no bytes are readable
        int length = 1;

        UnsynchronizedByteArrayInputStream stream =
            new UnsynchronizedByteArrayInputStream(singleByteArray, offsetAtEnd, length);

        assertEquals("No bytes should be available when offset is at the array boundary", 0, stream.available());

        int readResult = stream.read();
        assertEquals("read() should return END_OF_STREAM (-1) when no bytes remain", (-1), readResult);
    }
}
